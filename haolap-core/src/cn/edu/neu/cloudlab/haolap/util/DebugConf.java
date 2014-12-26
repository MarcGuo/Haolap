package cn.edu.neu.cloudlab.haolap.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DebugConf {
    private static boolean isDebug = false;
    private static boolean isHadoopDebug = false;
    private static Log log = LogFactory.getLog(DebugConf.class);

    static {
        boolean debugMode = false;
        boolean hadoopDebugMode = false;
        try {
            debugMode = Boolean.parseBoolean(System.getenv("HAOLAP_DEBUG_MODE"));
            hadoopDebugMode = Boolean.parseBoolean(System.getenv("HAOLAP_HADOOP_DEBUG_MODE"));
        } catch (NullPointerException e) {
            log.warn("The environment of DEBUG MODEs are not set");
        }
        isDebug = debugMode;
        isHadoopDebug = hadoopDebugMode;
    }

    public static boolean isHadoopDebug() {
        return isHadoopDebug;
    }

    public static boolean isDebug() {
        return isDebug;
    }
}
