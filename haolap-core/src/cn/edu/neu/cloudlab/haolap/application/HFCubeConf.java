package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.cube.Level;

public class HFCubeConf {
    public static final String VERSION = "";
    public static final String BASE_CUEB_NAME = "baseCube" + VERSION;
    public static final String BASE_SCHEMA_NAME = "TimeAreaDepth" + VERSION;
    // Dimension Information
    public static final String DIMENSION_TIME = "Time" + VERSION;
    public static final String DIMENSION_AREA = "Area" + VERSION;
    // Level: String name, long length, long begin, int orderNumber
    public static final String DIMENSION_DEPTH = "Depth" + VERSION;
    // Dimension[Time]'s levels
    public static final Level TIME_YEAR = new Level("year" + VERSION, 1L, 0L, 0);
    public static final Level TIME_SEASON = new Level("season" + VERSION, 4L,
            0L, 1);
    public static final Level TIME_MONTH = new Level("month" + VERSION, 3L, 0L,
            2);
    public static final Level TIME_DAY = new Level("day" + VERSION, 31L, 0L, 3);
    public static final Level TIME_SLOT = new Level("slot" + VERSION, 3L, 0L, 4);
    // Dimension[Area]'s levels
    public static final Level AREA_1 = new Level("1" + VERSION, 1L, 0L, 0);
    public static final Level AREA_2 = new Level("2" + VERSION, 2L, 0L, 1);
    public static final Level AREA_4 = new Level("4" + VERSION, 2L, 0L, 2);
    public static final Level AREA_8 = new Level("8" + VERSION, 2L, 0L, 3);
    public static final Level AREA_16 = new Level("16" + VERSION, 2L, 0L, 4);
    public static final Level AREA_32 = new Level("32" + VERSION, 2L, 0L, 5);
    public static final Level AREA_64 = new Level("64" + VERSION, 2L, 0L, 6);
    // Dimension[Depth]'s levels
    public static final Level DEPTH_100 = new Level("100m" + VERSION, 5L, 0L, 0);
    public static final Level DEPTH_50 = new Level("50m" + VERSION, 2L, 0L, 1);
    public static final Level DEPTH_10 = new Level("10m" + VERSION, 5L, 0L, 2);
    public static final String DATA_TYPE = "temperature" + VERSION;
    public static final String HFCUBE_SERVER_ADDR = "192.168.1.20";
    public static final int HFCUBE_SERVER_PORT = 5800;
    // Dimension Length Information
    public static final long DIMENSION_LENGTH_TIME = 3 * 1116 - 1;
    public static final long DIMENSION_LENGTH_AREA = 64 - 1;
    public static final long DIMENSION_LENGTH_DEPTH = 50 - 1;
    // Data's Information
    public static final double DATA_RANGE = 40.0;
}
