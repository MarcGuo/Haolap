package cn.edu.neu.cloudlab.haolap.util;

import org.hsqldb.lib.MD5;

public class RandomMd5Generator {
    public static String generate() {
        double seed = Math.random();
        String seedStr = String.valueOf(seed);
        return MD5.encodeString(seedStr, null);
    }
}
