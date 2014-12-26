package cn.edu.neu.cloudlab.haolap.RPC;

import cn.edu.neu.cloudlab.haolap.cube.Cube;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import org.apache.hadoop.ipc.VersionedProtocol;

import java.io.IOException;

public interface SchemaProtocol extends VersionedProtocol {
    public void printInfo(String info);

    public void addSchema(Schema Schema) throws SchemaAlreadyExistsException;

    public void removeSchema(String name);

    public Schema getSchema(String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException;

    public boolean isSchemaExist(String name);

    public Cube getCube(String cubeIdentifier) throws CubeNotExistsException;

    public boolean isCubeExist(String cubeIdentifier);

    public void addCube(Cube cube) throws CubeAlreadyExistsException;

    public void removeCube(String name);

    public void persistData() throws IOException;

    public void clear();

}
