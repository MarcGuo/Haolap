package cn.edu.neu.cloudlab.haolap.condition;

public class CubeCondition implements Condition {
    private final String cubeIdentifier;

    public CubeCondition(String cubeIdentifier) {
        this.cubeIdentifier = cubeIdentifier;
    }

    public String getCubeIdentifier() {
        return this.cubeIdentifier;
    }
}
