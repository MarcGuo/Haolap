package cn.edu.neu.cloudlab.haolap.mapreduce;

import cn.edu.neu.cloudlab.haolap.condition.AggregationCondition;
import cn.edu.neu.cloudlab.haolap.condition.AggregationCondition.Type;
import cn.edu.neu.cloudlab.haolap.operation.DoubleSumAggregation;
import cn.edu.neu.cloudlab.haolap.operation.Operation;
import org.apache.hadoop.io.DoubleWritable;

public class HFReducerImpl extends HFReducer {

    @Override
    protected Operation<DoubleWritable> newOperation(Type aggregationType) {
        if (aggregationType.equals(AggregationCondition.Type.sum)) {
            return new DoubleSumAggregation();
        }
        return null;
    }

}
