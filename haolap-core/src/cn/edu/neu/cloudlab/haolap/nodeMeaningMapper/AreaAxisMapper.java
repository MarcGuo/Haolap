package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.AxisWithoutMeaningErrorException;

public class AreaAxisMapper implements AxisMapper {
    private static final int minOne = 0;
    private static final int maxOne = 1 - 1;
    private static final int minOneForth = 0;
    private static final int maxOneForth = 16 - 1;
    private static final int minOneSixteenth = 0;
    private static final int maxOneSixteenth = 16 - 1;
    private static final String tail = "Square";

    @Override
    public String getAxisWithMeaning(long axisWithoutMeaning[])
            throws AxisWithoutMeaningErrorException {
        if (axisWithoutMeaning.length == 1) {
            valid((int) axisWithoutMeaning[0], minOneForth, minOneSixteenth);
            return axisWithoutMeaning[0] + "One" + tail;
        } else if (axisWithoutMeaning.length == 2) {
            valid((int) axisWithoutMeaning[0], (int) axisWithoutMeaning[1],
                    minOneSixteenth);
            return axisWithoutMeaning[0] * 16 + +axisWithoutMeaning[1]
                    + "OneForth" + tail;
        } else if (axisWithoutMeaning.length == 3) {
            valid((int) axisWithoutMeaning[0], (int) axisWithoutMeaning[1],
                    (int) axisWithoutMeaning[2]);
            return axisWithoutMeaning[0] * 16 * 16 + +axisWithoutMeaning[1] * 16
                    + axisWithoutMeaning[2] + "OneSixteen" + tail;
        } else {
            throw new AxisWithoutMeaningErrorException();
        }
    }

    private void valid(int areaOne, int areaOneForth, int areaOneSixteenth)
            throws AxisWithoutMeaningErrorException {
        if (areaOne < minOne || areaOne > maxOne) {
            throw new AxisWithoutMeaningErrorException();
        }
        if (areaOneForth < minOneForth || areaOneForth > maxOneForth) {
            throw new AxisWithoutMeaningErrorException();
        }
        if (areaOneSixteenth < minOneSixteenth
                || areaOneSixteenth > maxOneSixteenth) {
            throw new AxisWithoutMeaningErrorException();
        }
    }
}
