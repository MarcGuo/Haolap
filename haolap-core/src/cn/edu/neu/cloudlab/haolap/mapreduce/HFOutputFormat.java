package cn.edu.neu.cloudlab.haolap.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;

public class HFOutputFormat extends
        FileOutputFormat<Text, DoubleWritable> {

    public static CompressionType getOutputCompressionType(JobContext job) {
        String value = job.getConfiguration().get(
                "mapred.output.compression.type",
                CompressionType.RECORD.toString());
        return CompressionType.valueOf(value);
    }

    public static void setOutputCompressionType(Job job, CompressionType style) {
        setCompressOutput(job, true);
        job.getConfiguration().set("mapred.output.compression.type",
                style.toString());
    }

    @Override
    public RecordWriter<Text, DoubleWritable> getRecordWriter(
            TaskAttemptContext context) throws IOException,
            InterruptedException {
        Configuration conf = context.getConfiguration();
        CompressionCodec codec = null;
        CompressionType compressionType = CompressionType.NONE;
        if (getCompressOutput(context)) {
            // find the kind of compression to do
            compressionType = getOutputCompressionType(context);
            // find the right codec
            Class<?> codecClass = getOutputCompressorClass(context,
                    DefaultCodec.class);
            codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass,
                    conf);
        }
        // get the path of the temporary output file
        // TODO file name should be newCubeIdentifier + pagePosition
        Path file = getDefaultWorkFile(context, "");
        FileSystem fileSystem = file.getFileSystem(conf);
        final MapFile.Writer out = new MapFile.Writer(conf, fileSystem,
                file.toString(), Text.class,
                context.getOutputValueClass(), compressionType, codec, context);
        return new RecordWriter<Text, DoubleWritable>() {
            public void write(Text key, DoubleWritable value)
                    throws IOException {
//				System.out.println("value:\t" + value.getClass().toString());
                out.append(key, value);
            }

            public void close(TaskAttemptContext context) throws IOException {
                out.close();
            }
        };
    }

}
