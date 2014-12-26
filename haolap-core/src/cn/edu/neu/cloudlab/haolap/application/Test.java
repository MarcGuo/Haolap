package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import org.apache.hadoop.conf.Configuration;

/**
 * Created by marc on 14/12/26.
 */
public class Test {
    public static void main(String[] args) {

        Configuration conf = CubeConfiguration.getConfiguration();
        System.out.println(conf.get("rpc.schema.server.port"));

    }
}
