package cn.edu.neu.cloudlab.haolap.RPC;

import java.io.IOException;

public interface ServerInterface {
    public void start(String address, int port) throws IOException;

    public void stop();

    public void join() throws InterruptedException, IOException;

    public boolean isRunning();
}
