package cn.edu.neu.cloudlab.haolap.RPC;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public abstract class SchemaClientAbstract implements SchemaClientInterface {
    protected SchemaProtocol proxy;

    public SchemaClientAbstract() throws IOException {
        Configuration conf = CubeConfiguration.getConfiguration();
        InetSocketAddress addr = new InetSocketAddress(
                conf.get("rpc.schema.server.address"), Integer.valueOf(conf
                .get("rpc.schema.server.port")));
        proxy = (SchemaProtocol) RPC.waitForProxy(SchemaProtocol.class, 1,
                addr, conf);
    }

}
