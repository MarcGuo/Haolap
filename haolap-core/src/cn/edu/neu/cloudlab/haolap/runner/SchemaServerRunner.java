package cn.edu.neu.cloudlab.haolap.runner;

import cn.edu.neu.cloudlab.ToolsRunner;
import cn.edu.neu.cloudlab.haolap.service.RPCService;
import cn.edu.neu.cloudlab.haolap.service.SchemaServerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Marc Guo on 2014/10/9.
 */
public class SchemaServerRunner implements ToolsRunner {
    private static final Log log = LogFactory.getLog(SchemaServerRunner.class);

    @Override
    public void run(String[] args) throws Exception {
        RPCService rpcService = SchemaServerService.getSchemaServerService();
        rpcService.start();
        log.info("schema server is started");
    }

    @Override
    public String getName() {
        return "Schema Server";
    }
}
