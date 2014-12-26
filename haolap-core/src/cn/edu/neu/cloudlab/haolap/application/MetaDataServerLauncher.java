package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.socket.MetaDataNodeForHttpServer;

import java.io.IOException;

public class MetaDataServerLauncher {
    public static void main(String args[]) {
        MetaDataNodeForHttpServer server = new MetaDataNodeForHttpServer();
        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
