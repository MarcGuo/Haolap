package cn.edu.neu.cloudlab.haolap.socket;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MetaDataNodeForHttpServer {
    private int port = 5700;
    private int maxLengthOfQueue = 10;
    private ServerSocket providerSocket;
    private Socket connection = null;

    public MetaDataNodeForHttpServer() {
        Configuration conf = CubeConfiguration.getConfiguration();
        port = conf.getInt("meta.data.server.port", 5700);
        maxLengthOfQueue = conf.getInt("meta.data.server.maxLengthOfQueue", 10);

    }

    public void run() throws IOException {
        // create a server socket
        providerSocket = new ServerSocket(port, maxLengthOfQueue);
        // wait for connection
        while (true) {
            connection = providerSocket.accept();
            System.out.println("connection received from "
                    + connection.getInetAddress().getHostName());
            Thread thread = new Thread(new MetaDataNodeForHttpServerRequestDealer(connection));
            thread.start();
        }
        // providerSocket.close();
    }
}
