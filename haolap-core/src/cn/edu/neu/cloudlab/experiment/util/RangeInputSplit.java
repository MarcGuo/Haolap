package cn.edu.neu.cloudlab.experiment.util;

import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapred.InputSplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by MarcGuo on 6/1/14.
 */
public class RangeInputSplit implements InputSplit {
    long firstRow;
    long rowCount;

    public RangeInputSplit() {
    }

    public RangeInputSplit(long offset, long length) {
        firstRow = offset;
        rowCount = length;
    }

    public long getLength() throws IOException {
        return 0;
    }

    public String[] getLocations() throws IOException {
        return new String[]{};
    }

    public void readFields(DataInput in) throws IOException {
        firstRow = WritableUtils.readVLong(in);
        rowCount = WritableUtils.readVLong(in);
    }

    public void write(DataOutput out) throws IOException {
        WritableUtils.writeVLong(out, firstRow);
        WritableUtils.writeVLong(out, rowCount);
    }

}
