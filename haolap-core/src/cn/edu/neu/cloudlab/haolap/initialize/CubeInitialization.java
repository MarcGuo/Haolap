package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Cube;
import cn.edu.neu.cloudlab.haolap.exception.CubeAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;

public class CubeInitialization extends InitializationAbstract implements
        Initialization {
    SchemaClientInterface schemaClient;
    private String baseCubeIdentifier = SystemConf.getBaseCubeIdentifier();
    private String baseSchemaName = SystemConf.getBaseSchemaName();

    public CubeInitialization(Initialization next) {
        super(next);
    }

    @Override
    protected void initialize() throws CubeAlreadyExistsException,
            SchemaNotExistsException {
        schemaClient = SchemaClient.getSchemaClient();
        schemaClient.addCube(getBaseCube());
    }

    private Cube getBaseCube() throws SchemaNotExistsException {
        String dataType = "temperature";
        // the schema must be exist in Schema.schemas.
        if (!schemaClient.isSchemaExist(baseSchemaName)) {
            throw new SchemaNotExistsException(
                    "you should initialize schema first. once you initialize the schema, the "
                            + baseSchemaName + "will exist in Schema.schemas");
        }
        return new Cube(baseCubeIdentifier, dataType, baseSchemaName);
    }
}
