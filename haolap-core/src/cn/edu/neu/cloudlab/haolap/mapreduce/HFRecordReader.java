package cn.edu.neu.cloudlab.haolap.mapreduce;

import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.util.Serializer;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import cn.edu.neu.cloudlab.haolap.validation.PointValidation;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileRecordReader;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class HFRecordReader extends
        RecordReader<Text, DoubleWritable> {
    List<Long> startPointForElement;
    List<Long> endPointForElement;
    private SequenceFileRecordReader<Text, DoubleWritable> sequenceFileRecordReader;
    private Configuration conf;
    private Text key;
    private DoubleWritable value;
    private long fromDimensionLengths[];

    public HFRecordReader() {
        this.sequenceFileRecordReader = new SequenceFileRecordReader<Text, DoubleWritable>();
    }

    @Override
    public void close() throws IOException {
        this.sequenceFileRecordReader.close();
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return this.key;
    }

    @Override
    public DoubleWritable getCurrentValue() throws IOException, InterruptedException {
        return this.value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return this.sequenceFileRecordReader.getProgress();
    }

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        this.conf = context.getConfiguration();
        sequenceFileRecordReader.initialize(split, context);

        // get dimensionLengths from hadoop conf
        try {
            initDimensionLengthsFromHadoopConf();
        } catch (DimensionLengthsNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // get start&end from conf
        try {
            initStartAndEndFromHadoopConf();
        } catch (StartOrEndNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // check whether any problem with the process of getting the start&end
        if (startPointForElement == null || endPointForElement == null) {
            // TODO it's good to Debug but definitely not nice codes
            try {
                throw new CubeException(
                        "problem with the process of getting start&end from conf");
            } catch (CubeException e) {
                e.printStackTrace();
                // TODO exit is not a good way
                System.exit(-1);
            }
        }
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // filter the <key, value> pairs
        do {
            if (!this.sequenceFileRecordReader.nextKeyValue()) {
                return false;
            }
            // update <key, value>
            key = this.sequenceFileRecordReader.getCurrentKey();
            value = this.sequenceFileRecordReader.getCurrentValue();
            long pointArray[] = Serializer.deserialize(fromDimensionLengths,
                    new BigInteger(key.toString()));
            List<Long> point = new ArrayList<Long>();
            for (long l : pointArray) {
                point.add(l);
            }
            try {
                PointValidation.validate(point, startPointForElement,
                        endPointForElement);
            } catch (SizeNotEqualException e) {
                e.printStackTrace();
                // TODO exit is not a good way
                System.exit(-1);
            } catch (InvalidPointException e) {
                // if current <key, value> is not valid, continue the loop to
                // get the next <key, value>
                continue;
            }
            // no exceptions caught, the <key, value> is valid, return
            // immediately;
            return true;
        } while (true);
    }

    private void initStartAndEndFromHadoopConf()
            throws StartOrEndNotSetException {
        String startPointForElementStr = this.conf.get(SystemConf
                .getStartPointForConfSetString());
        String endPointForElementStr = this.conf.get(SystemConf
                .getEndPointForConfSetString());
        if (null == startPointForElementStr
                || startPointForElementStr.equals("")) {
            throw new StartOrEndNotSetException("start point not set");
        }
        if (null == endPointForElementStr || endPointForElementStr.equals("")) {
            throw new StartOrEndNotSetException("end point not set");
        }
        startPointForElement = new ArrayList<Long>();
        endPointForElement = new ArrayList<Long>();
        String tmp[] = startPointForElementStr.split(SystemConf
                .getHadoopConfDelimiter());
        for (String str : tmp) {
            startPointForElement.add(Long.valueOf(str));
        }
        tmp = endPointForElementStr.split(SystemConf.getHadoopConfDelimiter());
        for (String str : tmp) {
            endPointForElement.add(Long.valueOf(str));
        }
    }

    private void initDimensionLengthsFromHadoopConf()
            throws DimensionLengthsNotSetException {
        String fromDimensionLengthsStr = this.conf.get(SystemConf
                .getFromDimensionLengthsForConfSetString());
        if (null == fromDimensionLengthsStr || "".equals(fromDimensionLengthsStr)) {
            throw new DimensionLengthsNotSetException();
        }
        String tmp[] = fromDimensionLengthsStr.split(SystemConf
                .getHadoopConfDelimiter());
        int length = tmp.length;
        this.fromDimensionLengths = new long[length];
        for (int i = 0; i < length; i += 1) {
            fromDimensionLengths[i] = Long.valueOf(tmp[i]);
        }
    }
}
