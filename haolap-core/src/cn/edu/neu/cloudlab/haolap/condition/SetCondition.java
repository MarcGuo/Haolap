package cn.edu.neu.cloudlab.haolap.condition;

import cn.edu.neu.cloudlab.haolap.cube.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class SetCondition implements Condition {
    private SortedSet<StartEndPair> pairs = new TreeSet<StartEndPair>();

    public SetCondition() {
        super();
    }

    public void addStartEndPair(Dimension dimension, long start, long end) {
        this.pairs
                .add(new StartEndPair(dimension.getOrderNumber(), start, end));
    }

    public List<Long> getStartPoint() {
        List<Long> startPoint = new ArrayList<Long>();
        for (StartEndPair pair : pairs) {
            startPoint.add(pair.getStart());
        }
        return startPoint;
    }

    public List<Long> getEndPoint() {
        List<Long> endPoint = new ArrayList<Long>();
        for (StartEndPair pair : pairs) {
            endPoint.add(pair.getEnd());
        }
        return endPoint;
    }

    public List<Integer> getDimensionOrders() {
        List<Integer> orders = new ArrayList<Integer>();
        for (StartEndPair pair : pairs) {
            orders.add(pair.getDimensionOrderNumber());
        }
        return orders;
    }
}
