package cn.edu.neu.cloudlab.haolap.socket;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JobServer {
    private static Log log = LogFactory.getLog(JobServer.class);
    private int port = 5800;
    private int maxLengthOfQueue = 10;
    private ServerSocket providerSocket;
    private Socket connection = null;

    public JobServer() {
        Configuration conf = CubeConfiguration.getConfiguration();
        port = conf.getInt("job.server.port", 5800);
        maxLengthOfQueue = conf.getInt("job.server.maxLengthOfQueue", 10);
    }

    public void run() throws IOException {
        // create a server socket
        providerSocket = new ServerSocket(port, maxLengthOfQueue);
        // wait for connection
        while (true) {
            connection = providerSocket.accept();
            log.info("connection received from "
                    + connection.getInetAddress().getHostName());
            Thread thread = new Thread(new JobServerRequestDealer(connection));
            thread.start();
        }
        // providerSocket.close();
    }
}
