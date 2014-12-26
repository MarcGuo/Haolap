package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CorrespondingDimensionNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.CubeXmlFileNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.PathConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PersistSchemaReader {
    private SortedSet<Dimension> dimensions;
    private PersistDimensionReader persistDimensionReader;

    public SortedSet<Schema> read() throws CubeXmlFileNotExistsException,
            IOException, DocumentException,
            CorrespondingDimensionNotExistsException {
        dimensions = readDimensions();
        SortedSet<Schema> schemas = readSchemas();
        return schemas;
    }

    private SortedSet<Schema> readSchemas() throws IOException,
            CubeXmlFileNotExistsException, DocumentException,
            CorrespondingDimensionNotExistsException {
        Configuration conf = CubeConfiguration.getConfiguration();
        FileSystem hdfs = FileSystem.get(conf);
        Path schemaXmlFile = new Path(PathConf.getSchemaXmlFilePath());
        if (!hdfs.exists(schemaXmlFile)) {
            throw new CubeXmlFileNotExistsException();
        }
        FSDataInputStream in = hdfs.open(schemaXmlFile);
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        List<?> schemaList = document.selectNodes("//schemas/schema");
        Node schemaNode;
        String schemaName;
        String dimensionName;
        Dimension correspondingDimension;
        SortedSet<Dimension> correspondingDimensions = new TreeSet<Dimension>();
        SortedSet<Schema> schemas = new TreeSet<Schema>();
        Node dimensionNameNode;
        for (Iterator<?> iteratorForSchema = schemaList.iterator(); iteratorForSchema
                .hasNext(); ) {
            schemaNode = (Node) iteratorForSchema.next();
            schemaName = schemaNode.selectSingleNode("name").getText();
            List<?> dimensionNameNodeList = schemaNode
                    .selectNodes("dimensionName");
            for (Iterator<?> iteratorForDimensionName = dimensionNameNodeList
                    .iterator(); iteratorForDimensionName.hasNext(); ) {
                dimensionNameNode = (Node) iteratorForDimensionName.next();
                dimensionName = dimensionNameNode.getText();
                correspondingDimension = findCorrespondingDimension(
                        dimensionName, dimensions);
                correspondingDimensions.add(correspondingDimension);
            }
            schemas.add(Schema.getSchema(schemaName, correspondingDimensions));
        }
        return schemas;
    }

    private Dimension findCorrespondingDimension(String dimensionName,
                                                 SortedSet<Dimension> dimensions)
            throws CorrespondingDimensionNotExistsException {
        if (null == dimensionName) {
            throw new CorrespondingDimensionNotExistsException();
        }
        for (Dimension dimension : dimensions) {
            if (dimensionName.equals(dimension.getName())) {
                return dimension;
            }
        }
        throw new CorrespondingDimensionNotExistsException();
    }

    private SortedSet<Dimension> readDimensions()
            throws CubeXmlFileNotExistsException, IOException,
            DocumentException {
        persistDimensionReader = new PersistDimensionReader();
        return persistDimensionReader.read();
    }
}
