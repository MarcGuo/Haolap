package cn.edu.neu.cloudlab.haolap.service;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaServer;

import java.io.IOException;

public class SchemaServerService implements RPCService {
    // there's only one SchemaServerService running at the same time.
    private static RPCService uniqueInstance = new SchemaServerService();
    private SchemaServer schemaServer = SchemaServer.getSchemaServer();

    private SchemaServerService() {

    }

    public static RPCService getSchemaServerService() {
        return SchemaServerService.uniqueInstance;
    }

    @Override
    public void start() {
        try {
            this.schemaServer.start(null, -1);
            this.schemaServer.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        this.schemaServer.stop();
    }

    @Override
    public boolean isRunning() {
        return this.schemaServer.isRunning();
    }
}
