package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.DimensionRange;
import cn.edu.neu.cloudlab.haolap.cube.Level;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.SchemaAlreadyExistsException;
import cn.edu.neu.cloudlab.haolap.util.DebugConf;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;

import java.util.SortedSet;
import java.util.TreeSet;

public class SchemaInitialization extends InitializationAbstract implements
        Initialization {
    SchemaClientInterface schemaClient;
    private String baseSchemaName = SystemConf.getBaseSchemaName();

    public SchemaInitialization(Initialization next) {
        super(next);
    }

    @Override
    protected void initialize() throws SchemaAlreadyExistsException {
        schemaClient = SchemaClient.getSchemaClient();
        schemaClient.addSchema(getBaseSchema());
    }

    private Schema getBaseSchema() {
        if (DebugConf.isDebug()) {
            SortedSet<Dimension> dimensions = new TreeSet<Dimension>();
            dimensions.add(getDebugDimension());
            dimensions.add(getDebugDimension());
            dimensions.add(getDebugDimension());
            Schema schema = Schema.getSchema(baseSchemaName, dimensions);
            return schema;
        }
        SortedSet<Dimension> dimensions = new TreeSet<Dimension>();
        dimensions.add(getTimeDimension());
        dimensions.add(getAreaDimension());
        dimensions.add(getDepthDimension());
        Schema schema = Schema.getSchema(baseSchemaName, dimensions);
        return schema;
    }

    private Dimension getDebugDimension() {
        Level level01 = new Level("year", 1L, 0L, 0);
        Level level02 = new Level("month", 3L, 0L, 1);
        Level level03 = new Level("day", 3L, 0L, 2);

        SortedSet<Level> levels = new TreeSet<Level>();
        levels.add(level01);
        levels.add(level02);
        levels.add(level03);
        DimensionRange dimensionRange = new DimensionRange(0L, 10L);

        Dimension dimension = Dimension.getDimension("Time", levels, 0,
                dimensionRange);
        return dimension;
    }

    private Dimension getTimeDimension() {
        Level level01 = new Level("year", 1L, 0L, 0);
        Level level02 = new Level("month", 12L, 0L, 1);
        Level level03 = new Level("day", 31L, 0L, 2);

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
