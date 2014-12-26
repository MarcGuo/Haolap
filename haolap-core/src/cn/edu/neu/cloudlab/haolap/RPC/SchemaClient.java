package cn.edu.neu.cloudlab.haolap.RPC;

import cn.edu.neu.cloudlab.haolap.cube.Cube;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.io.IOException;

public class SchemaClient extends SchemaClientAbstract implements
        SchemaClientInterface {
    private static SchemaClientInterface uniqueInstance;

    static {
        try {
            uniqueInstance = new SchemaClient();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private SchemaClient() throws IOException {
        super();
    }

    public static SchemaClientInterface getSchemaClient() {
        return uniqueInstance;
    }

    @Override
    public void printInfo() {
        System.out.println("connecting...");
        this.proxy.printInfo("connected.");
        System.out.println("connected.");
    }

    @Override
    public void addSchema(Schema schema) throws SchemaAlreadyExistsException {
        this.proxy.addSchema(schema);
    }

    @Override
    public void addCube(Cube cube) throws CubeAlreadyExistsException {
        this.proxy.addCube(cube);
    }

    @Override
    public Cube getCube(String cubeIdentifier) throws CubeNotExistsException {
        return this.proxy.getCube(cubeIdentifier);
    }

    @Override
    public boolean isSchemaExist(String schemaName) {
        return this.proxy.isSchemaExist(schemaName);
    }

    @Override
    public Schema getSchema(String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException {
        return this.proxy.getSchema(cubeIdentifier);
    }

    @Override
    public void persistData() throws IOException {
        this.proxy.persistData();
    }

    @Override
    public void removeCube(String cubeIdentifier) {
        this.proxy.removeCube(cubeIdentifier);
    }

    @Override
    public void removeSchema(String schemaName) {
        this.proxy.removeSchema(schemaName);
    }

    @Override
    public void clear() {
        this.proxy.clear();
    }

    @Override
    public boolean isCubeExist(String cubeIdentifier) {
        return this.proxy.isCubeExist(cubeIdentifier);
    }

}
