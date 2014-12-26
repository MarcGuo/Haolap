package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.PageFullException;
import cn.edu.neu.cloudlab.haolap.exception.PersistErrorException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.PageHelper;
import cn.edu.neu.cloudlab.haolap.util.PathConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private String cubeIdentifier;
    private long[] pageLengths;
    private long position;
    private List<CubeElement<Double>> elements;

    public Page(String cubeIdentifier, long position,
                List<CubeElement<Double>> elements) throws SchemaNotExistsException,
            CubeNotExistsException {
        super();
        this.cubeIdentifier = cubeIdentifier;
        this.position = position;
        this.pageLengths = PageHelper.getPageLengths(cubeIdentifier);
        this.elements = elements;
    }

    public Page(String pageFile) throws SchemaNotExistsException,
            CubeNotExistsException, IOException {
        super();
        String temp[] = pageFile.split("/");
        this.cubeIdentifier = temp[temp.length - 2];
        this.position = Long.valueOf(temp[temp.length - 1]);
        this.pageLengths = PageHelper.getPageLengths(cubeIdentifier);
        this.elements = getElementsFrom();
    }

    public String getCubeIdentifier() {
        return cubeIdentifier;
    }

    public long getPosition() {
        return position;
    }

    public List<CubeElement<Double>> getElements() {
        return elements;
    }

    // integer(-2147483648 ~ 2147483648) is big enough to a page
    public List<CubeElement<Double>> getElements(int startPosition,
                                                 int endPositoin) {
        return this.elements.subList(startPosition, endPositoin);
    }

    public void addCubeElement(CubeElement<Double> cubeElement)
            throws PageFullException {
        if (this.elements.size() < getCapacity()) {
            this.elements.add(cubeElement);
        } else {
            throw new PageFullException(
                    "cannot add an element to a page that already full.");
        }
    }

    public void persist() throws PersistErrorException, IOException {
        Configuration conf = CubeConfiguration.getConfiguration();
        FileSystem hdfs = FileSystem.get(conf);
        Path path = new Path(getFileName());

        if (isExsist(path)) {
            hdfs.delete(path, true);
        }
        LongWritable key = new LongWritable();
        DoubleWritable value = new DoubleWritable();
        MapFile.Writer writer = new MapFile.Writer(conf, hdfs, path.toString(),
                key.getClass(), value.getClass());
        for (CubeElement<Double> element : elements) {
            key.set(element.getPosition());
            value.set(element.getData());
            writer.append(key, value);
        }
        writer.close();
        IOUtils.closeStream(writer);
        System.out.println(getFileName() + " written.");
    }

    private List<CubeElement<Double>> getElementsFrom() throws IOException {
        Configuration conf = CubeConfiguration.getConfiguration();
        String fileName = getFileName();
        FileSystem hdfs = FileSystem.get(conf);
        Path path = new Path(fileName);
        MapFile.Reader reader = new MapFile.Reader(hdfs, path.toString(), conf);
        WritableComparable<?> key = (WritableComparable<?>) ReflectionUtils
                .newInstance(reader.getKeyClass(), conf);
        Writable value = (Writable) ReflectionUtils.newInstance(
                reader.getValueClass(), conf);
        List<CubeElement<Double>> elements = new ArrayList<CubeElement<Double>>();
        while (reader.next(key, value)) {
            elements.add(new CubeElement<Double>(((LongWritable) key).get(),
                    ((DoubleWritable) value).get()));
        }
        reader.close();
        return elements;
    }

    private String getFileName() {
        return PathConf.getCubeElementPath() + this.cubeIdentifier + "/"
                + this.position;
    }

    private long getCapacity() {
        long capacity = 1;
        for (int i = 0; i < this.pageLengths.length; i += 1) {
            capacity *= this.pageLengths[i];
        }
        return capacity;
    }

    private boolean isExsist(Path path) throws IOException {
        Configuration conf = CubeConfiguration.getConfiguration();
        FileSystem hdfs = FileSystem.get(conf);
        return hdfs.exists(path);
    }

}
