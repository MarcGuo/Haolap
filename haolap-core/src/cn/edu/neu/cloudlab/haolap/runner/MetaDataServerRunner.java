package cn.edu.neu.cloudlab.haolap.runner;

import cn.edu.neu.cloudlab.ToolsRunner;
import cn.edu.neu.cloudlab.haolap.socket.MetaDataNodeForHttpServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by Marc Guo on 2014/10/9.
 */
public class MetaDataServerRunner implements ToolsRunner {
    private static final Log log = LogFactory.getLog(MetaDataServerRunner.class);

    @Override
    public void run(String[] args) throws Exception {
        MetaDataNodeForHttpServer server = new MetaDataNodeForHttpServer();
        try {
            server.run();
            log.info("meta data server is started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Meta Data Server";
    }
}
