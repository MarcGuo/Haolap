package cn.edu.neu.cloudlab.haolap.condition;

public class AggregationCondition implements Condition {
    private final Type aggregationType;

    public AggregationCondition(Type aggregationType) {
        super();
        this.aggregationType = aggregationType;
    }

    public Type getAggregationType() {
        return aggregationType;
    }

    public enum Type {
        sum, average, max, min
    }
}
