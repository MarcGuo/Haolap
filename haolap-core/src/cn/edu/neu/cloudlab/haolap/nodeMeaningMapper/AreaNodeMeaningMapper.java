package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.NodeMeaningKeyErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;

public class AreaNodeMeaningMapper implements NodeMeaningMapper {
    private static final int minOne = 0;
    private static final int maxOne = 1 - 1;
    private static final int minOneForth = 0;
    private static final int maxOneForth = 16 - 1;
    private static final int minOneSixteenth = 0;
    private static final int maxOneSixteenth = 16 - 1;

    @Override
    public String getMeaning(String key) throws NumberFormatException,
            NodeMeaningKeyErrorException {
        String parts[] = key.split(SystemConf.getNodeMeaningKeyDelimiter());
        if (parts.length == 1) {
            return map(Integer.valueOf(parts[0]), minOneForth, minOneSixteenth);
        } else if (parts.length == 2) {
            return map(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
                    minOneSixteenth);
        } else if (parts.length == 3) {
            return map(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]),
                    Integer.valueOf(parts[2]));
        } else {
            throw new NodeMeaningKeyErrorException();
        }
    }

    private String map(int areaOne, int areaOneForth, int areaOneSixteenth)
            throws NodeMeaningKeyErrorException {
        if (areaOne < minOne || areaOne > maxOne) {
            throw new NodeMeaningKeyErrorException();
        }
        if (areaOneForth < minOneForth || areaOneForth > maxOneForth) {
            throw new NodeMeaningKeyErrorException();
        }
        if (areaOneSixteenth < minOneSixteenth
                || areaOneSixteenth > maxOneSixteenth) {
            throw new NodeMeaningKeyErrorException();
        }
        areaOne -= minOne;
        areaOneForth -= minOneForth;
        areaOneSixteenth -= minOneSixteenth;
        int areaOneForthLength = maxOneForth - minOneForth + 1;
        int areaOneSixteenthLength = maxOneSixteenth - minOneSixteenth + 1;
        int value = areaOne * areaOneForthLength * areaOneSixteenthLength
                + areaOneForth * areaOneSixteenthLength + areaOneSixteenth;
        return String.valueOf(value);
    }
}
