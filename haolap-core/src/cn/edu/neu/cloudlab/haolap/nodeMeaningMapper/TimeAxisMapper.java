package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.AxisWithoutMeaningErrorException;

public class TimeAxisMapper implements AxisMapper {
    private static final int baseYear = 1850;
    private static final int baseMonth = 0;
    private static final int baseDay = 0;
    private static final int minYear = 0;
    private static final int maxYear = 1 - 1;
    private static final int minMonth = 0;
    private static final int maxMonth = 12 - 1;
    private static final int minDay = 0;
    private static final int maxDay = 31 - 1;
    private static final String delimiter = "-";

    @Override
    public String getAxisWithMeaning(long axisWithoutMeaning[])
            throws AxisWithoutMeaningErrorException {
        for (long axis : axisWithoutMeaning) {
            System.err.print(axis);
        }
        System.err.println();
        if (axisWithoutMeaning.length == 1) {
            valid((int) axisWithoutMeaning[0], minMonth, minDay);
            return "" + (axisWithoutMeaning[0] + baseYear);
        } else if (axisWithoutMeaning.length == 2) {
            valid((int) axisWithoutMeaning[0], (int) axisWithoutMeaning[1],
                    minDay);
            return "" + (axisWithoutMeaning[0] + baseYear) + delimiter
                    + (axisWithoutMeaning[1] + baseMonth);
        } else if (axisWithoutMeaning.length == 3) {
            valid((int) axisWithoutMeaning[0], (int) axisWithoutMeaning[1],
                    (int) axisWithoutMeaning[2]);
            return "" + (axisWithoutMeaning[0] + baseYear) + delimiter
                    + (axisWithoutMeaning[1] + baseMonth) + delimiter
                    + (axisWithoutMeaning[2] + baseDay);
        } else {
            throw new AxisWithoutMeaningErrorException();
        }
    }

    private void valid(int year, int month, int day)
            throws AxisWithoutMeaningErrorException {
        if (year < minYear || year > maxYear) {
            throw new AxisWithoutMeaningErrorException();
        }
        if (month < minMonth || month > maxMonth) {
            throw new AxisWithoutMeaningErrorException();
        }
        if (day < minDay || day > maxDay) {
            throw new AxisWithoutMeaningErrorException();
        }
    }
}
