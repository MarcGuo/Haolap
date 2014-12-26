package cn.edu.neu.cloudlab.haolap.condition;

public class FullCondition {
    private final CubeCondition cubeCondition;
    private final SetCondition setCondition;
    private final TargetCondition targetCondition;
    private final AggregationCondition aggregationCondition;

    public FullCondition(CubeCondition cubeCondition,
                         SetCondition setCondition, TargetCondition targetCondition,
                         AggregationCondition aggregationCondition) {
        super();
        this.cubeCondition = cubeCondition;
        this.setCondition = setCondition;
        this.targetCondition = targetCondition;
        this.aggregationCondition = aggregationCondition;
    }

    public CubeCondition getCubeCondition() {
        return cubeCondition;
    }

    public SetCondition getSetCondition() {
        return setCondition;
    }

    public TargetCondition getTargetCondition() {
        return targetCondition;
    }

    public AggregationCondition getAggregationCondition() {
        return aggregationCondition;
    }
}
