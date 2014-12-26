package cn.edu.neu.cloudlab.haolap.mapreduce;

import cn.edu.neu.cloudlab.haolap.condition.AggregationCondition;
import cn.edu.neu.cloudlab.haolap.condition.AggregationCondition.Type;
import cn.edu.neu.cloudlab.haolap.exception.AggregationTypeNotSetException;
import cn.edu.neu.cloudlab.haolap.exception.CubeIdentifierNotSetException;
import cn.edu.neu.cloudlab.haolap.exception.NewCubeIdentifierNotSetException;
import cn.edu.neu.cloudlab.haolap.operation.Operation;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public abstract class HFReducer extends
        Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    private Configuration conf;
    private Operation<DoubleWritable> operation;
    private AggregationCondition.Type aggregationType;
    private String newCubeIdentifier;

    @Override
    protected void setup(Context context) {
        // initialize conf
        this.conf = context.getConfiguration();

        // initialize aggregationType
        try {
            initAggregationTypeFromHadoopConf();
        } catch (CubeIdentifierNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        } catch (AggregationTypeNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // initialize newCubeIdentifier
        try {
            initNewCubeIdentifierFromHadoopConf();
        } catch (CubeIdentifierNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        } catch (NewCubeIdentifierNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // initialize operation
        initOperation();

    }

    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values,
                          Context context) throws IOException, InterruptedException {
        DoubleWritable collectedValue = operation.getInitialValue();
        long numberOfValues = 0;
        for (DoubleWritable value : values) {
            operation.collect(collectedValue, value);
            numberOfValues += 1;
        }
//		System.out.println("key: " + key.get() + "\t" + "numberOfValues: "
//				+ numberOfValues);
        DoubleWritable aggregatedValue = operation.aggregate(collectedValue,
                numberOfValues);
//		System.out.println(key.get() + "\t" + aggregatedValue.get());
        context.write(key, aggregatedValue);
    }

    private void initOperation() {
        this.operation = newOperation(this.aggregationType);
    }

    private void initAggregationTypeFromHadoopConf()
            throws CubeIdentifierNotSetException,
            AggregationTypeNotSetException {
        this.aggregationType = AggregationCondition.Type.valueOf(this.conf
                .get(SystemConf.getAggregationTypeForConfSetString()));
        if (null == aggregationType || aggregationType.equals("")) {
            throw new AggregationTypeNotSetException("aggregationType missing");
        }
    }

    private void initNewCubeIdentifierFromHadoopConf()
            throws CubeIdentifierNotSetException,
            NewCubeIdentifierNotSetException {
        this.newCubeIdentifier = this.conf.get(SystemConf
                .getNewCubeIdentifierForConfSetString());
        if (null == newCubeIdentifier || newCubeIdentifier.equals("")) {
            throw new NewCubeIdentifierNotSetException(
                    "newCubeIdentifier missing");
        }
    }

    protected abstract Operation<DoubleWritable> newOperation(
            Type aggregationType);
}
