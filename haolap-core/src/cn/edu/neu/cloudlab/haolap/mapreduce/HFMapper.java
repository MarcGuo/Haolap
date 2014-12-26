package cn.edu.neu.cloudlab.haolap.mapreduce;

import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Level;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.util.Serializer;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class HFMapper extends
        Mapper<Text, DoubleWritable, Text, DoubleWritable> {
    private Configuration conf;
    private String fromCubeIdentifier;
    private long fromDimensionLengths[];
    private long newDimensionLengths[];
    private Schema fromSchema;
    private Schema newSchema;

    @Override
    protected void setup(Context context) {
        this.conf = context.getConfiguration();

        // get cube identifier from hadoop conf
        try {
            initCubeIdentifierFromHadoopConf();
        } catch (CubeIdentifierNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // get dimensionLengths from hadoop conf
        try {
            initDimensionLengthsFromHadoopConf();
        } catch (DimensionLengthsNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // get newDimensionLengths from hadoop conf
        try {
            initNewDimensionLengthsFromHadoopConf();
        } catch (DimensionLengthsNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // get schema from hadoop conf
        try {
            initSchemaFromConf();
        } catch (InvalidObjectStringException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        } catch (SchemaNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }

        // get newSchema from hadoop conf
        try {
            initNewSchemaFromConf();
        } catch (InvalidObjectStringException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        } catch (NewSchemaNotSetException e) {
            e.printStackTrace();
            // TODO exit is not a good way
            System.exit(-1);
        }
    }

    @Override
    protected void map(Text key, DoubleWritable value, Context context)
            throws IOException, InterruptedException {

        // deserialize LongWritable key
        BigInteger elementNo = new BigInteger(key.toString());
        long pointArray[] = Serializer.deserialize(fromDimensionLengths,
                elementNo);
        List<Long> point = new ArrayList<Long>();
        for (long l : pointArray) {
            point.add(l);
        }

        // change key's level
        List<Long> newPoint = new ArrayList<Long>();
        SortedSet<Dimension> dimensions = fromSchema.getDimensions();
        SortedSet<Dimension> newDimensions = newSchema.getDimensions();
        // System.out.println(fromSchema);
        // System.out.println(newSchema);
        Dimension correspondingDimension;
        long dimensionLocationNo;
        long dimensionLocation[];
        long correspondingDimensionLocation[];
        long correspondingDimensionLocationNo;
        int i = 0;
        for (Dimension dimension : dimensions) {
            dimensionLocationNo = point.get(i);
            try {
                correspondingDimension = findCorrespondingDimension(dimension,
                        newDimensions);
                dimensionLocation = findLocation(dimension, dimensionLocationNo);
                correspondingDimensionLocation = constructCorrespondingLocation(
                        correspondingDimension, dimensionLocation);
                correspondingDimensionLocationNo = findLocationNo(
                        correspondingDimension, correspondingDimensionLocation);
                newPoint.add(correspondingDimensionLocationNo);
            } catch (CorrespondingDimensionNotExistsException e) {
                // all locations map to a not exist location, and not added into
                // newPoint
                correspondingDimensionLocationNo = -1;
            }
            i += 1;
        }

        // serialize
        BigInteger newElementNo = Serializer.serialize(newDimensionLengths,
                newPoint);
        // write <key, value>
        Text newKey = new Text(newElementNo.toString());
        // System.out.println(newKey);
        context.write(newKey, value);
    }

    private void initCubeIdentifierFromHadoopConf()
            throws CubeIdentifierNotSetException {
        this.fromCubeIdentifier = this.conf.get(SystemConf
                .getFromCubeIdentifierForConfSetString());
        if (null == fromCubeIdentifier || fromCubeIdentifier.equals("")) {
            throw new CubeIdentifierNotSetException("cube identifier missing");
        }
    }

    private void initDimensionLengthsFromHadoopConf()
            throws DimensionLengthsNotSetException {
        String fromDimensionLengthsStr = this.conf.get(SystemConf
                .getFromDimensionLengthsForConfSetString());
        if (null == fromDimensionLengthsStr
                || "".equals(fromDimensionLengthsStr)) {
            throw new DimensionLengthsNotSetException();
        }
        String tmp[] = fromDimensionLengthsStr.split(SystemConf
                .getHadoopConfDelimiter());
        int length = tmp.length;
        this.fromDimensionLengths = new long[length];
        for (int i = 0; i < length; i += 1) {
            fromDimensionLengths[i] = Long.valueOf(tmp[i]);
        }
//		System.out.println("fromDimensionLengths : "
//				+ Arrays.toString(this.fromDimensionLengths));
    }

    private void initNewDimensionLengthsFromHadoopConf()
            throws DimensionLengthsNotSetException {
        String newDimensionLengthsStr = this.conf.get(SystemConf
                .getNewDimensionLengthsForConfSetString());
        if (null == newDimensionLengthsStr || "".equals(newDimensionLengthsStr)) {
            throw new DimensionLengthsNotSetException();
        }
        String tmp[] = newDimensionLengthsStr.split(SystemConf
                .getHadoopConfDelimiter());
        int length = tmp.length;
        this.newDimensionLengths = new long[length];
        for (int i = 0; i < length; i += 1) {
            newDimensionLengths[i] = Long.valueOf(tmp[i]);
        }
    }

    private void initSchemaFromConf() throws InvalidObjectStringException,
            SchemaNotSetException {
        String fromSchemaStr = this.conf.get(SystemConf
                .getFromSchemaForConfSetString());
        if (null == fromSchemaStr || "".equals(fromSchemaStr)) {
            throw new SchemaNotSetException();
        }
        this.fromSchema = Schema.constructedFromString(fromSchemaStr);
    }

    private void initNewSchemaFromConf() throws InvalidObjectStringException,
            NewSchemaNotSetException {
        String newSchemaStr = this.conf.get(SystemConf
                .getNewSchemaForConfSetString());
        if (null == newSchemaStr || "".equals(newSchemaStr)) {
            throw new NewSchemaNotSetException();
        }
        this.newSchema = Schema.constructedFromString(newSchemaStr);
    }

    private Dimension findCorrespondingDimension(Dimension dimension,
                                                 SortedSet<Dimension> dimensions)
            throws CorrespondingDimensionNotExistsException {
        for (Dimension d : dimensions) {
            if (dimension.isCorrespondingTo(d)) {
                return d;
            }
        }
        throw new CorrespondingDimensionNotExistsException();
    }

    private long[] findLocation(Dimension dimension, long locationNo) {
        SortedSet<Level> levels = dimension.getLevels();
        long[] levelLengths = new long[levels.size()];
        int j = 0;
        for (Level level : levels) {
            levelLengths[j] = level.getLength();
            j += 1;
        }
        return Serializer.deserialize(levelLengths,
                new BigInteger(String.valueOf(locationNo)));
    }

    private long[] constructCorrespondingLocation(
            Dimension correspondingDimension, long dimensionLocation[]) {
        long correspondingDimensionLocation[];
        SortedSet<Level> correspondingLevels = correspondingDimension
                .getLevels();
        int numberOfLevelsRemained = correspondingLevels.size();
        correspondingDimensionLocation = new long[numberOfLevelsRemained];
        for (int j = 0; j < numberOfLevelsRemained; j += 1) {
            correspondingDimensionLocation[j] = dimensionLocation[j];
        }
        return correspondingDimensionLocation;
    }

    private long findLocationNo(Dimension correspondingDimension,
                                long correspondingDimensionLocation[]) {
        SortedSet<Level> levels = correspondingDimension.getLevels();
        long[] levelLengths = new long[levels.size()];
        int j = 0;
        for (Level level : levels) {
            levelLengths[j] = level.getLength();
            j += 1;
        }
        return Serializer.serialize(levelLengths,
                correspondingDimensionLocation).longValue();
    }
}
