package cn.edu.neu.cloudlab.haolap.operation;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Writable;

public class DoubleSumAggregation implements Operation<DoubleWritable> {
    @Override
    public Class<? extends Writable> getValueClass() {
        return DoubleWritable.class;
    }

    @Override
    public DoubleWritable getInitialValue() {
        return new DoubleWritable(0.0);
    }

    @Override
    public void collect(DoubleWritable collectedValue, DoubleWritable valueRight) {
        collectedValue.set(collectedValue.get() + valueRight.get());
    }

    @Override
    public DoubleWritable aggregate(DoubleWritable collectedValue,
                                    long numberOfValues) {
        return collectedValue;
    }
}
