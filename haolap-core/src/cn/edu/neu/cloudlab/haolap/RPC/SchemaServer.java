package cn.edu.neu.cloudlab.haolap.RPC;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.cube.*;
import cn.edu.neu.cloudlab.haolap.exception.CubeAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.DebugConf;
import cn.edu.neu.cloudlab.haolap.util.PathConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SchemaServer implements ServerInterface, SchemaProtocol {

    private static final long versionId = 1L;
    // there's only one server in the same time.
    private static SchemaServer uniqueInstance = new SchemaServer();
    private Server server;
    private Set<Schema> schemas = new HashSet<Schema>();
    private Set<Cube> cubes = new HashSet<Cube>();
    private boolean isRunning = false;

    private SchemaServer() {

    }

    public static SchemaServer getSchemaServer() {
        return SchemaServer.uniqueInstance;
    }

    @Override
    public long getProtocolVersion(String arg0, long arg1) throws IOException {
        return SchemaServer.versionId;
    }

    @Override
    public void printInfo(String info) {
        System.out.println("schema server: version " + SchemaServer.versionId);
        System.out.println(info);
    }

    @Override
    public void addSchema(Schema schema) throws SchemaAlreadyExistsException {
        if (this.isExist(schema)) {
            throw new SchemaAlreadyExistsException();
        } else {
            this.schemas.add(schema);
        }
    }

    @Override
    public void removeSchema(String name) {
        for (Schema schema : this.schemas) {
            if (name.equals(schema.getName())) {
                this.schemas.remove(schema);
                return;
            }
        }
    }

    @Override
    public Schema getSchema(String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException {
        String schemaName = "";
        schemaName = getSchemaName(cubeIdentifier);
        // find schema by schema name
        for (Schema schema : schemas) {
            if (schemaName.equals(schema.getName())) {
                return schema;
            }
        }
        throw new SchemaNotExistsException("schema: " + schemaName
                + " not exists");
    }

    @Override
    public boolean isSchemaExist(String name) {
        if (name == null) {
            return false;
        }
        for (Schema schemaInSet : this.schemas) {
            if (name.equals(schemaInSet.getName()))
                return true;
        }
        return false;
    }

    @Override
    public Cube getCube(String cubeIdentifier) throws CubeNotExistsException {
        if (cubeIdentifier == null) {
            throw new CubeNotExistsException("cube: " + cubeIdentifier
                    + " not exists");
        }
        for (Cube cube : cubes) {
            if (cube.getIdentifier().equals(cubeIdentifier)) {
                return cube;
            }
        }
        throw new CubeNotExistsException("cube: " + cubeIdentifier
                + " not exists");
    }

    @Override
    public boolean isCubeExist(String cubeIdentifier) {
        if (null == cubeIdentifier) {
            return false;
        }
        for (Cube cube : this.cubes) {
            if (cubeIdentifier.equals(cube.getIdentifier())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addCube(Cube cube) throws CubeAlreadyExistsException {
        if (this.isExist(cube)) {
            throw new CubeAlreadyExistsException();
        } else {
            this.cubes.add(cube);
        }
    }

    @Override
    public void removeCube(String cubeIdentifier) {
        for (Cube cube : this.cubes) {
            if (cubeIdentifier.equals(cube.getIdentifier())) {
                this.cubes.remove(cube);
                return;
            }
        }
    }

    @Override
    public void persistData() throws IOException {
        persistSchemas();
        psersistDimensions();
        persistCubes();
    }

    @Override
    public void clear() {
        schemas.clear();
        cubes.clear();
        SchemaPool.getSchemaPool().clear();
        DimensionPool.getDimensionPool().clear();
    }

    @Override
    public void start(String address, int port) throws IOException {
        Configuration conf = CubeConfiguration.getConfiguration();
        if (null == address || "".equals(address)) {
            address = conf.get("rpc.schema.server.address");
        }
        if (port <= 0) {
            port = Integer.valueOf(conf.get("rpc.schema.server.port"));
        }
        if (this.isRunning) {
            return;
        }
        this.server = RPC.getServer(this, address, port, conf);
        this.server.start();
        this.isRunning = true;
    }

    @Override
    public void stop() {
        if (!this.isRunning) {
            return;
        }
        this.server.stop();
        this.isRunning = false;
    }

    @Override
    public void join() throws InterruptedException, IOException {
        if (!this.isRunning) {
            this.start(null, -1);
        }
        this.server.join();
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    private boolean isExist(Schema schema) {
        for (Schema schemaInSet : this.schemas) {
            if (schemaInSet.equals(schema))
                return true;
        }
        return false;
    }

    private boolean isExist(Cube cube) {
        for (Cube cubeInSet : this.cubes) {
            if (cubeInSet.equals(cube))
                return true;
        }
        return false;
    }

    private String getSchemaName(String cubeIdentifier)
            throws CubeNotExistsException {
        for (Cube cube : cubes) {
            if (cubeIdentifier.equals(cube.getIdentifier())) {
                return cube.getSchemaName();
            }
        }
        throw new CubeNotExistsException();
    }

    private void persistCubes() throws IOException {
        Document cubeXml = DocumentHelper.createDocument();
        Element cubeRoot = cubeXml.addElement("cubes");
        Element cubeElement;
        for (Cube cube : cubes) {
            cubeElement = cubeRoot.addElement("cube");
            cubeElement.addElement("identifier").addText(cube.getIdentifier());
            cubeElement.addElement("dataType").addText(cube.getDataType());
            cubeElement.addElement("schemaName").addText(cube.getSchemaName());
        }
        if (DebugConf.isHadoopDebug()) {
            FileWriter cubeFileWriter = new FileWriter(
                    PathConf.getCubeXmlFilePath());
            cubeXml.write(cubeFileWriter);
            cubeFileWriter.close();
        } else {
            Path hdfsFile = new Path(PathConf.getCubeXmlFilePath());
            FileSystem hdfs = FileSystem.get(CubeConfiguration
                    .getConfiguration());
            FSDataOutputStream out = hdfs.create(hdfsFile);
            out.write(cubeXml.asXML().getBytes());
            out.close();
            hdfs.close();
        }
    }

    private void psersistDimensions() throws IOException {
        Set<Dimension> persistedDimensions = new HashSet<Dimension>();
        Document dimensionXml = DocumentHelper.createDocument();
        Element dimensionRoot = dimensionXml.addElement("dimensions");
        Element dimensionElement;
        Element levelElement;
        Set<Level> levels;
        Set<Dimension> dimensions;
        Element rangeElement;
        for (Schema schema : schemas) {
            dimensions = schema.getDimensions();
            for (Dimension dimension : dimensions) {
                if (persistedDimensions.contains(dimension)) {
                    continue;
                } else {
                    persistedDimensions.add(dimension);
                }
                dimensionElement = dimensionRoot.addElement("dimension");
                // persist name
                dimensionElement.addElement("name")
                        .addText(dimension.getName());
                // persist levels
                levels = dimension.getLevels();
                for (Level level : levels) {
                    levelElement = dimensionElement.addElement("level");
                    levelElement.addElement("name").addText(level.getName());
                    levelElement.addElement("length").addText(
                            String.valueOf(level.getLength()));
                    levelElement.addElement("begin").addText(
                            String.valueOf(level.getBegin()));
                    levelElement.addElement("orderNumber").addText(
                            String.valueOf(level.getOrderNumber()));
                }
                // persist orderNumber
                dimensionElement.addElement("orderNumber").addText(
                        String.valueOf(dimension.getOrderNumber()));
                // persist dimensionRange
                DimensionRange dimensionRange = dimension.getRange();
                rangeElement = dimensionElement.addElement("range");
                rangeElement.addElement("start").addText(
                        String.valueOf(dimensionRange.getStart()));
                rangeElement.addElement("end").addText(
                        String.valueOf(dimensionRange.getEnd()));
            }
        }
        if (DebugConf.isHadoopDebug()) {

            FileWriter dimensionFileWriter = new FileWriter(
                    PathConf.getDimensionXmlFilePath());
            dimensionXml.write(dimensionFileWriter);
            dimensionFileWriter.close();
        } else {
            Path hdfsFile = new Path(PathConf.getDimensionXmlFilePath());
            FileSystem hdfs = FileSystem.get(CubeConfiguration
                    .getConfiguration());
            FSDataOutputStream out = hdfs.create(hdfsFile);
            out.write(dimensionXml.asXML().getBytes());
            out.close();
            hdfs.close();
        }
    }

    private void persistSchemas() throws IOException {
        Document schemaXml = DocumentHelper.createDocument();
        Element schemaRoot = schemaXml.addElement("schemas");
        Element schemaElement;
        Set<Dimension> dimensions;
        for (Schema schema : schemas) {
            schemaElement = schemaRoot.addElement("schema");
            schemaElement.addElement("name").addText(schema.getName());
            dimensions = schema.getDimensions();
            for (Dimension dimension : dimensions) {
                schemaElement.addElement("dimensionName").addText(
                        dimension.getName());
            }
        }
        if (DebugConf.isHadoopDebug()) {
            FileWriter schemaFileWriter = new FileWriter(
                    PathConf.getSchemaXmlFilePath());
            schemaXml.write(schemaFileWriter);
            schemaFileWriter.close();
        } else {
            Path hdfsFile = new Path(PathConf.getSchemaXmlFilePath());
            FileSystem hdfs = FileSystem.get(CubeConfiguration
                    .getConfiguration());
            FSDataOutputStream out = hdfs.create(hdfsFile);
            out.write(schemaXml.asXML().getBytes());
            out.close();
            hdfs.close();
        }
    }
}
