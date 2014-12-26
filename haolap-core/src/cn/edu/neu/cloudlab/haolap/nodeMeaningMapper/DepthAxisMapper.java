package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.AxisWithoutMeaningErrorException;

public class DepthAxisMapper implements AxisMapper {
    private static final int min50m = 0;
    private static final int max50m = 10 - 1;
    private static final int min10m = 0;
    private static final int max10m = 5 - 1;
    private static final int min1m = 0;
    private static final int max1m = 10 - 1;
    private static final String tail = "m";

    @Override
    public String getAxisWithMeaning(long axisWithoutMeaning[])
            throws AxisWithoutMeaningErrorException {
        if (axisWithoutMeaning.length == 1) {
            valid((int) axisWithoutMeaning[0], min10m, min1m);
            return axisWithoutMeaning[0] + "-50" + tail;
        } else if (axisWithoutMeaning.length == 2) {
            valid((int) axisWithoutMeaning[0], (int) axisWithoutMeaning[1],
                    min1m);
            return axisWithoutMeaning[0] * 5 + +axisWithoutMeaning[1] + "-10"
                    + tail;
        } else if (axisWithoutMeaning.length == 3) {
            valid((int) axisWithoutMeaning[0], (int) axisWithoutMeaning[1],
                    (int) axisWithoutMeaning[2]);
            return axisWithoutMeaning[0] * 50 + axisWithoutMeaning[1] * 10
                    + axisWithoutMeaning[2] * 1 + "-1" + tail;
        } else {
            throw new AxisWithoutMeaningErrorException();
        }
    }

    private void valid(int depth50m, int depth10m, int depth1m)
            throws AxisWithoutMeaningErrorException {
        if (depth50m < min50m || depth50m > max50m) {
            throw new AxisWithoutMeaningErrorException();
        }
        if (depth10m < min10m || depth10m > max10m) {
            throw new AxisWithoutMeaningErrorException();
        }
        if (depth1m < min1m || depth1m > max1m) {
            throw new AxisWithoutMeaningErrorException();
        }
    }
}
