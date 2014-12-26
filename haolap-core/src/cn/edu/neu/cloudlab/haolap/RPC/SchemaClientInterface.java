package cn.edu.neu.cloudlab.haolap.RPC;

import cn.edu.neu.cloudlab.haolap.cube.Cube;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.io.IOException;

public interface SchemaClientInterface extends ClientInterface {

    public void addSchema(Schema schema) throws SchemaAlreadyExistsException;

    public boolean isSchemaExist(String schemaName);

    public Schema getSchema(String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException;

    public void persistData() throws IOException;

    public boolean isCubeExist(String cubeIdentifier);

    public Cube getCube(String cubeIdentifier) throws CubeNotExistsException;

    public void addCube(Cube cube) throws CubeAlreadyExistsException;

    public void removeCube(String cubeIdentifier);

    public void removeSchema(String schemaName);

    public void clear();
}
