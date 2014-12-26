package cn.edu.neu.cloudlab.haolap.condition;

import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Level;

import java.util.SortedSet;
import java.util.TreeSet;

public class TargetCondition implements Condition {
    private SortedSet<DimensionLevelPair> pairs = new TreeSet<DimensionLevelPair>();

    public TargetCondition() {
        super();
    }

    public void addDimensionLevel(Dimension dimension, Level level) {
        this.pairs.add(new DimensionLevelPair(dimension.getOrderNumber(), level
                .getOrderNumber()));
    }

    public SortedSet<DimensionLevelPair> getPairs() {
        return this.pairs;
    }
}
