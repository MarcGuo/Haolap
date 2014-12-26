package cn.edu.neu.cloudlab.haolap.operation;

import org.apache.hadoop.io.Writable;

public interface Operation<V extends Writable> {
    public Class<? extends Writable> getValueClass();

    public V getInitialValue();

    public void collect(V collectedValue, V valueRight);

    public V aggregate(V collectedValue, long numberOfValues);
}
