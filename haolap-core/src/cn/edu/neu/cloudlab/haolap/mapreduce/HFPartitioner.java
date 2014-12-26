package cn.edu.neu.cloudlab.haolap.mapreduce;

import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.InvalidObjectStringException;
import cn.edu.neu.cloudlab.haolap.exception.NewSchemaNotSetException;
import cn.edu.neu.cloudlab.haolap.util.PageHelper;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Partitioner;

import java.math.BigInteger;

public class HFPartitioner<DoubleWritable extends Writable> extends
        Partitioner<Text, DoubleWritable> implements Configurable {
    Configuration conf;

    @Override
    public int getPartition(Text key, DoubleWritable value,
                            int numberOfPartitions) {
        String newSchemaStr = conf.get(SystemConf
                .getNewSchemaForConfSetString());
        if (null == newSchemaStr || "".equals(newSchemaStr)) {
            try {
                throw new NewSchemaNotSetException();
            } catch (NewSchemaNotSetException e) {
                e.printStackTrace();
                // TODO exit is not a good way
                System.exit(-1);
            }
        }
        Schema newSchema = null;
        try {
            newSchema = Schema.constructedFromString(newSchemaStr);
//			System.err.println(newSchemaStr);
        } catch (InvalidObjectStringException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }
        BigInteger elementNo = new BigInteger(key.toString());
        // TODO numberOfPages cannot bigger than Integer.MAX_VALUE
        int pageNo = (int) PageHelper.belongTo(elementNo, newSchema);
//		System.err.println("pageNo: " + pageNo);
        if (pageNo != 0) {
//			System.err.println("*******************************************");
        }
        return pageNo;
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

}
