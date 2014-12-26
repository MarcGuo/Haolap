package cn.edu.neu.cloudlab.dataloader;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientToUser;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.DimensionRange;
import cn.edu.neu.cloudlab.haolap.cube.Level;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class SchemaInitialization extends InitializationAbstract implements
        Initialization {
    SchemaClientInterface schemaClient;
    private String baseSchemaName = "timeAreaDepth";

    public SchemaInitialization(Initialization next) {
        super(next);
    }

    @Override
    protected void initialize() throws SchemaAlreadyExistsException, IOException {
        Configuration conf = new Configuration();
        conf.set("rpc.schema.server.address", "localhost");
        conf.setInt("rpc.schema.server.port", 9510);
        schemaClient = new SchemaClientToUser(conf);
        schemaClient.addSchema(getBaseSchema());
    }

    private Schema getBaseSchema() {
        SortedSet<Dimension> dimensions = new TreeSet<Dimension>();
        dimensions.add(getTimeDimension());
        dimensions.add(getAreaDimension());
        dimensions.add(getDepthDimension());
        Schema schema = Schema.getSchema(baseSchemaName, dimensions);
        return schema;
    }

    private Dimension getTimeDimension() {
//        Level level01 = new Level("year", 1L, 0L, 0);
//        Level level02 = new Level("month", 12L, 0L, 1);
//        Level level03 = new Level("day", 31L, 0L, 2);

        Level level01 = new Level("year", 1L, 0L, 0);
        Level level02 = new Level("month", 3L, 0L, 1);
        Level level03 = new Level("day", 3L, 0L, 2);

        SortedSet<Level> levels = new TreeSet<Level>();
        levels.add(level01);
        levels.add(level02);
        levels.add(level03);
        DimensionRange dimensionRange = new DimensionRange(0L, 372L - 1L);

        Dimension dimension = Dimension.getDimension("Time", levels, 0,
                dimensionRange);
        return dimension;
    }

    private Dimension getAreaDimension() {
        Level level01 = new Level("one", 1L, 0L, 0);
        Level level02 = new Level("oneForth", 16L, 0L, 1);
        Level level03 = new Level("oneSixteenth", 16L, 0L, 2);

        SortedSet<Level> levels = new TreeSet<Level>();
        levels.add(level01);
        levels.add(level02);
        levels.add(level03);
        DimensionRange dimensionRange = new DimensionRange(0L, 256L - 1L);

        Dimension dimension = Dimension.getDimension("Area", levels, 1,
                dimensionRange);
        return dimension;
    }

    private Dimension getDepthDimension() {
        Level level01 = new Level("50m", 10L, 0L, 0);
        Level level02 = new Level("10m", 5L, 0L, 1);
        Level level03 = new Level("1m", 10L, 0L, 2);

        SortedSet<Level> levels = new TreeSet<Level>();
        levels.add(level01);
        levels.add(level02);
        levels.add(level03);

        DimensionRange dimensionRange = new DimensionRange(0L, 500L - 1L);
        Dimension dimension = Dimension.getDimension("Depth", levels, 2,
                dimensionRange);
        return dimension;
    }
}
