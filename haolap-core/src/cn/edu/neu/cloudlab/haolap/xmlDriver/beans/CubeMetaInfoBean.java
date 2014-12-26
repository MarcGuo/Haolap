package cn.edu.neu.cloudlab.haolap.xmlDriver.beans;

public class CubeMetaInfoBean {
    private final String cubeIdentifier;

    public CubeMetaInfoBean(String cubeIdentifier) {
        super();
        this.cubeIdentifier = cubeIdentifier;
    }

    public String getCubeIdentifier() {
        return cubeIdentifier;
    }
}
