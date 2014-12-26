package cn.edu.neu.cloudlab.haolap.xmlDriver.beans;

public class NodeMeaningBean {
    private final String cubeIdentifier;
    private final String dimensionName;
    private final String type;
    private final String value;

    public NodeMeaningBean(String cubeIdentifier, String dimensionName,
                           String type, String value) {
        super();
        this.cubeIdentifier = cubeIdentifier;
        this.dimensionName = dimensionName;
        this.type = type;
        this.value = value;
    }

    public String getCubeIdentifier() {
        return cubeIdentifier;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
