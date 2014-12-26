package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.exception.InvalidObjectStringException;

public class DimensionRange {
    private static final String regex = "^DimensionRange *\\[start=[0-9]+, *end=[0-9]+\\]$";
    private final long start;
    private final long end;

    public DimensionRange(long start, long end) {
        super();
        this.start = start;
        this.end = end;
    }

    public static String getRegex() {
        return regex;
    }

    public static DimensionRange constructFromString(String dimensionRangeStr)
            throws InvalidObjectStringException {
        long start;
        long end;
        if (!dimensionRangeStr.matches(regex)) {
            throw new InvalidObjectStringException("object string("
                    + dimensionRangeStr + ") not matches: " + regex);
        }
        String tmp[] = dimensionRangeStr.split("\\[");
        tmp = tmp[1].split("\\]");
        String fields[] = tmp[0].split(",");
        start = Long.valueOf(fields[0].split("=")[1]);
        end = Long.valueOf(fields[1].split("=")[1]);
        return new DimensionRange(start, end);
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "DimensionRange [start=" + start + ", end=" + end + "]";
    }
}
