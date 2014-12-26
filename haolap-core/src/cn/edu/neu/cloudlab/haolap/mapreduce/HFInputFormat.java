package cn.edu.neu.cloudlab.haolap.mapreduce;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;
import java.util.List;

public class HFInputFormat extends
        FileInputFormat<Text, DoubleWritable> {

    @Override
    public RecordReader<Text, DoubleWritable> createRecordReader(InputSplit inputSplit,
                                                                 TaskAttemptContext taskAttemptContext) throws IOException,
            InterruptedException {
        return new HFRecordReader();
    }

    @Override
    protected long getFormatMinSplitSize() {
        return SequenceFile.SYNC_INTERVAL;
    }

    @Override
    protected List<FileStatus> listStatus(JobContext jobContext)
            throws IOException {
        List<FileStatus> files = super.listStatus(jobContext);
        int len = files.size();
        for (int i = 0; i < len; i += 1) {
            FileStatus file = files.get(i);
            if (file.isDir()) {
                Path path = file.getPath();
                FileSystem fileSystem = path.getFileSystem(jobContext
                        .getConfiguration());
                // use the data file // TODO not understand
                files.set(i, fileSystem.getFileStatus(new Path(path,
                        MapFile.DATA_FILE_NAME)));
            }
        }
        return files;
    }
}
