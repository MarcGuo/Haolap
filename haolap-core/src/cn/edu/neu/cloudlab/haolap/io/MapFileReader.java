package cn.edu.neu.cloudlab.haolap.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

public class MapFileReader {
    public static void main(String[] args) {
        String uri = "hdfs://cloud00:9000/HFCube/cubeElement/baseCube/1";
        Configuration conf = new Configuration();
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(uri), conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = new Path(uri);
        MapFile.Reader reader = null;
        try {
            reader = new MapFile.Reader(fs, path.toString(), conf);
            WritableComparable<?> key = (WritableComparable<?>) ReflectionUtils
                    .newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(
                    reader.getValueClass(), conf);
            while (reader.next(key, value)) {
                System.out.println(key + "\t" + value);
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
