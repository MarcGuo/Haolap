package cn.edu.neu.cloudlab.haolap.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

public class SequenceFileReader {
    public static void main(String[] args) {
        String uri = "hdfs://localhost:9000/HFCube/cubeElement/baseCube/1";
        Configuration conf = new Configuration();
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(uri), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = new Path(uri);
        SequenceFile.Reader reader = null;
        try {
            reader = new SequenceFile.Reader(fs, path, conf);
            Writable key = (Writable) ReflectionUtils.newInstance(
                    reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(
                    reader.getValueClass(), conf);
            long position = reader.getPosition();
            while (reader.next(key, value)) {
                String syncSeen = reader.syncSeen() ? "*" : "";
                System.out.println("[" + position + "]\t" + syncSeen + "\t"
                        + key + "\t" + value);
                position = reader.getPosition();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
