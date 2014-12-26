package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.NodeMeaningKeyErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;

public class DepthNodeMeaningMapper implements NodeMeaningMapper {
    private static final int min50m = 0;
    private static final int max50m = 10 - 1;
    private static final int min10m = 0;
    private static final int max10m = 5 - 1;
    private static final int min1m = 0;
    private static final int max1m = 10 - 1;

    @Override
    public String getMeaning(String key) throws NumberFormatException,
            NodeMeaningKeyErrorException {
        String parts[] = key.split(SystemConf.getNodeMeaningKeyDelimiter());
        if (parts.length == 1) {
            return map(Integer.valueOf(parts[0]), min10m, min1m);
        } else if (parts.length == 2) {
            return map(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
                    min1m);
        } else if (parts.length == 3) {
            return map(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
                    Integer.valueOf(parts[2]));
        } else {
            throw new NodeMeaningKeyErrorException();
        }
    }

    private String map(int depth50m, int depth10m, int depth1m)
            throws NodeMeaningKeyErrorException {
        if (depth50m < min50m || depth50m > max50m) {
            throw new NodeMeaningKeyErrorException();
        }
        if (depth10m < min10m || depth10m > max10m) {
            throw new NodeMeaningKeyErrorException();
        }
        if (depth1m < min1m || depth1m > max1m) {
            throw new NodeMeaningKeyErrorException();
        }
        depth50m -= min50m;
        depth10m -= min10m;
        depth1m -= min1m;
        int depth10mLength = max10m - min10m + 1;
        int depth1mLength = max1m - min1m + 1;
        int value = depth50m * depth10mLength * depth1mLength + depth10m
                * depth1mLength + depth1m;
        return String.valueOf(value);
    }
}
