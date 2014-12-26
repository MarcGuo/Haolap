package cn.edu.neu.cloudlab.haolap.runner;

import cn.edu.neu.cloudlab.ToolsRunner;
import cn.edu.neu.cloudlab.haolap.socket.JobServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by Marc Guo on 2014/10/9.
 */
public class JobServerRunner implements ToolsRunner {
    private static final Log log = LogFactory.getLog(JobServerRunner.class);

    @Override
    public void run(String[] args) throws Exception {
        JobServer jobServer = new JobServer();
        try {
            jobServer.run();
            log.info("job server is started");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return "Job Server";
    }
}
