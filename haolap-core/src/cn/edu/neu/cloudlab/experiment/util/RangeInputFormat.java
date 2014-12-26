package cn.edu.neu.cloudlab.experiment.util;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.*;

import java.io.IOException;

/**
 * An input format that assigns ranges of longs to each mapper.
 */
@SuppressWarnings("deprecation")
public class RangeInputFormat implements
        InputFormat<LongWritable, NullWritable> {

    public static long getNumberOfData(JobConf job) {
        return job.getLong("data.node.num", 0);
    }

    public static void setNumberOfData(JobConf job, long nodeNum) {
        job.setLong("data.node.num", nodeNum);
    }

    public RecordReader<LongWritable, NullWritable> getRecordReader(
            InputSplit split, JobConf job, Reporter reporter)
            throws IOException {
        return new RangeRecordReader((RangeInputSplit) split);
    }

    /**
     * Create the desired number of splits, dividing the number of rows
     * between the mappers.
     */
    public InputSplit[] getSplits(JobConf job, int numSplits) {
        long totalNodes = getNumberOfData(job);
        long nodesPerSplits = totalNodes / numSplits;
        InputSplit[] splits = new InputSplit[numSplits];
        long currentNode = 0;
        for (int i = 0; i < numSplits - 1; ++i) {
            splits[i] = new RangeInputSplit(currentNode, nodesPerSplits);
            currentNode += nodesPerSplits;
        }
        splits[numSplits - 1] = new RangeInputSplit(currentNode, totalNodes
                - currentNode);
        return splits;
    }


    /**
     * A record reader that will generate a range of numbers.
     */
    static class RangeRecordReader implements
            RecordReader<LongWritable, NullWritable> {
        long startRow;
        long finishedRows;
        long totalRows;

        public RangeRecordReader(RangeInputSplit split) {
            startRow = split.firstRow;
            finishedRows = 0;
            totalRows = split.rowCount;
        }

        public void close() throws IOException {
            // NOTHING
        }

        public LongWritable createKey() {
            return new LongWritable();
        }

        public NullWritable createValue() {
            return NullWritable.get();
        }

        public long getPos() throws IOException {
            return finishedRows;
        }

        public float getProgress() throws IOException {
            return finishedRows / (float) totalRows;
        }

        public boolean next(LongWritable key, NullWritable value) {
            if (finishedRows < totalRows) {
                key.set(startRow + finishedRows);
                finishedRows += 1;
                return true;
            } else {
                return false;
            }
        }

    }
}