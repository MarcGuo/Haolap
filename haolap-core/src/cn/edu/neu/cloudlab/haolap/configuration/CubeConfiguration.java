package cn.edu.neu.cloudlab.haolap.configuration;

import cn.edu.neu.cloudlab.util.ConfigurationLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;

/**
 * This class provide a way to get the system's configurations. The
 * configuration files, named "cube-default" and "cube-core.xml" will be loaded
 * into the program.
 *
 * @author Neoh
 * @update Marc
 */
public class CubeConfiguration {
    private static Configuration conf;
    private static Log log = LogFactory.getLog(CubeConfiguration.class);

    static {
        Configuration.addDefaultResource("core-default.xml");
        Configuration.addDefaultResource("core-site.xml");
        Configuration.addDefaultResource("hdfs-default.xml");
        Configuration.addDefaultResource("hdfs-site.xml");
        Configuration.addDefaultResource("mapred-default.xml");
        Configuration.addDefaultResource("mapred-site.xml");
        Configuration.addDefaultResource("cube-default.xml");
        Configuration.addDefaultResource("cube-site.xml");
        Configuration.addDefaultResource("cloudlab-experiment-default.xml");
        Configuration.addDefaultResource("cloudlab-experiment-site.xml");
        conf = new Configuration();


    }

    private CubeConfiguration() {

    }

    private static void addLocalResource(String resourceName) {
        ConfigurationLoader.addLocalResource(conf, resourceName);
    }

    private static void addExteranlResource(String resourceName) {
        ConfigurationLoader.addExteranlResource(conf, resourceName);
    }

    public static Configuration getConfiguration() {
        return conf;
    }

    public static Configuration getCopyOfConfiguration(Configuration conf) {
        return new Configuration(conf);
    }
}
