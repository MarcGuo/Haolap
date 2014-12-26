package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.service.RPCService;
import cn.edu.neu.cloudlab.haolap.service.SchemaServerService;

public final class SchemaServer {
    public static void main(String argv[]) {
        RPCService rpcService = SchemaServerService.getSchemaServerService();
        rpcService.start();


    }
}

