package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.cube.Cube;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.util.PageHelper;
import cn.edu.neu.cloudlab.haolap.util.PathConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.SortedSet;

public class Initializer {

    public Initializer() {
        super();
    }

    public void initializeRandomData() throws SchemaAlreadyExistsException,
            CubeAlreadyExistsException, PersistErrorException,
            PageFullException, SchemaNotExistsException,
            CubeNotExistsException, IOException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        schemaClient.clear();
        Initialization initialization = new SchemaInitialization(
                new CubeInitialization(new CubeElementsInitialization(null)));
//        Initialization initialization = new SchemaInitialization(null);
        initialization.doInitialize();
        schemaClient.persistData();
    }

    public void initializeServerWithPersistDataInServer() throws IOException,
            CubeXmlFileNotExistsException, DocumentException,
            CubeAlreadyExistsException,
            CorrespondingDimensionNotExistsException,
            SchemaAlreadyExistsException,
            CorrespondingSchemaNotExistsException,
            CubeElementsNotExistsException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        // read Schemas
        PersistSchemaReader persistSchemaReader = new PersistSchemaReader();
        SortedSet<Schema> schemas = persistSchemaReader.read();
        // read cubes
        PersistCubeReader persistCubeReader = new PersistCubeReader();
        SortedSet<Cube> cubes = persistCubeReader.read();
        // check whether cubes, schemas and cubeElements consistent
        String schemaName;
        String cubeIdentifier;
        long numberOfPages;
        for (Cube cube : cubes) {
            schemaName = cube.getSchemaName();
            // if not find, CorrespondingSchemaNotExistsException will be thrown
            Schema schema = findCorrespondingSchema(schemaName, schemas);
            cubeIdentifier = cube.getIdentifier();
            numberOfPages = PageHelper.getNumberOfPages(schema);
            if (!isCubeElementsExists(cubeIdentifier, numberOfPages)) {
                throw new CubeElementsNotExistsException();
            }
        }
        // add schemas
        for (Schema schema : schemas) {
            schemaClient.addSchema(schema);
        }
        // add cubes
        for (Cube cube : cubes) {
            schemaClient.addCube(cube);
        }
    }

    private boolean isCubeElementsExists(String cubeIdentifier,
                                         long numberOfPages) throws IOException {
        Configuration conf = CubeConfiguration.getConfiguration();
        FileSystem hdfs = FileSystem.get(conf);
        String pageFileName;
        for (int pageNo = 0; pageNo < numberOfPages; pageNo += 1) {
            pageFileName = PathConf.getCubeElementPath() + cubeIdentifier + "/"
                    + pageNo;
            Path pageFile = new Path(pageFileName);
            if (!hdfs.exists(pageFile)) {
                return false;
            }
        }
        return true;
    }

    private Schema findCorrespondingSchema(String schemaName,
                                           SortedSet<Schema> schemas)
            throws CorrespondingSchemaNotExistsException {
        if (null == schemaName) {
            throw new CorrespondingSchemaNotExistsException();
        }
        for (Schema schema : schemas) {
            if (schemaName.equals(schema.getName())) {
                return schema;
            }
        }
        throw new CorrespondingSchemaNotExistsException();
    }
}
