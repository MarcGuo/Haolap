package cn.edu.neu.cloudlab.haolap.RPC;

import cn.edu.neu.cloudlab.haolap.cube.Cube;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by marc on 14/11/29.
 */
public class SchemaClientToUser implements
        SchemaClientInterface {

    protected SchemaProtocol proxy;

    public SchemaClientToUser(Configuration conf) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(
                conf.get("rpc.schema.server.address"), Integer.valueOf(conf
                .get("rpc.schema.server.port")));
        proxy = (SchemaProtocol) RPC.waitForProxy(SchemaProtocol.class, 1,
                addr, conf);
    }

    @Override
    public void printInfo() {
        this.proxy.printInfo("connected.");
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
