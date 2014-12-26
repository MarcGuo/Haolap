package cn.edu.neu.cloudlab.haolap.xmlDriver.beans;

public class ConditionBean {
    private final String dimensionName;
    private String levelName;
    private String start;
    private String end;

    public ConditionBean(String dimensionName, String levelName, String start,
                         String end) {
        super();
        this.dimensionName = dimensionName;
        this.levelName = levelName;
        this.start = start;
        this.end = end;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
