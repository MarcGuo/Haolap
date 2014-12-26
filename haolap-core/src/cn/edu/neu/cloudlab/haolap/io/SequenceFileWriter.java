package cn.edu.neu.cloudlab.haolap.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

public class SequenceFileWriter {
    private static final String[] data = {"One, two, bucklemy whoe",
            "Three, four, shut the door", "Five, six, pick up sticks",
            "Seven, eight , lay them straight", "Nine, ten, a big fat hen"};

    public static void main(String[] args) {
        String uri = "./debugResource/cubeElement/test.seq";
        Configuration conf = new Configuration();
        FileSystem hdfs = null;
        try {
            hdfs = FileSystem.get(URI.create(uri), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = new Path(uri);
        IntWritable key = new IntWritable();
        Text value = new Text();
        SequenceFile.Writer writer = null;
        try {
            writer = SequenceFile.createWriter(hdfs, conf, path, key.getClass(),
                    value.getClass());
            for (int i = 0; i < 100; i += 1) {
                key.set(100 - i);
                value.set(data[i % data.length]);
                System.out.println("[" + writer.getLength() + "]\t" + key
                        + "\t" + value);
                writer.append(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(writer);
        }
    }
}
