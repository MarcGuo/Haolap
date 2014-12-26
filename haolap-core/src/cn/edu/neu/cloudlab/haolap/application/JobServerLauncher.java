package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.socket.JobServer;

import java.io.IOException;

public class JobServerLauncher {
    public static void main(String args[]) {

        cn.edu.neu.cloudlab.haolap.socket.JobServer jobServer = new JobServer();
        try {
            jobServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
