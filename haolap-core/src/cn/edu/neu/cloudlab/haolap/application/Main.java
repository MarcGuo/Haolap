package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.initialize.CubeInitialization;
import cn.edu.neu.cloudlab.haolap.initialize.Initialization;
import cn.edu.neu.cloudlab.haolap.initialize.SchemaInitialization;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws SchemaAlreadyExistsException,
            CubeAlreadyExistsException, PersistErrorException,
            PageFullException, SchemaNotExistsException,
            CubeNotExistsException, IOException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        schemaClient.clear();
        Initialization initialization = new SchemaInitialization(
                new CubeInitialization(null));
        initialization.doInitialize();
        schemaClient.persistData();
    }
}
