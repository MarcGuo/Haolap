package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.socket.JobTestClient;
import cn.edu.neu.cloudlab.haolap.socket.MetaDataNodeForHttpServerTestClient;

import java.io.IOException;

public class Client {
    public static void main(String args[]) {
        JobTestClient jobClient = new JobTestClient();
        try {
            jobClient.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MetaDataNodeForHttpServerTestClient metaDataNodeForHttpServerClient = new MetaDataNodeForHttpServerTestClient();
        try {
            metaDataNodeForHttpServerClient.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
