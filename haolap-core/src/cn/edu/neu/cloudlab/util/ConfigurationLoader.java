package cn.edu.neu.cloudlab.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Marc Guo on 2014/10/5.
 */
public class ConfigurationLoader {
    private static final Log log = LogFactory.getLog(ConfigurationLoader.class);

    public static void addLocalResource(Configuration conf, String resourceName) {
        InputStream resourceStream = ConfigurationLoader.class
                .getClassLoader().getResourceAsStream(resourceName);
        if (resourceStream == null) {
            log.warn("Can't find default resource please re-build");
            throw new RuntimeException(String.format("the default resource [%s] is not found", resourceName));
        }
        conf.addResource(resourceStream);
    }

    public static void addExteranlResource(Configuration conf, String resourceName) {
        Path resourcePath = new Path(resourceName);
        try {
            FileSystem localFileSystem = FileSystem.getLocal(new Configuration());
            if (!localFileSystem.exists(resourcePath) || !localFileSystem.isFile(resourcePath)) {
                throw new IOException("The external file " + resourceName + " is not exists");
            }

        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        conf.addResource(resourcePath);
    }
}
