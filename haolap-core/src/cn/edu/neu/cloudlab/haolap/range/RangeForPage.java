package cn.edu.neu.cloudlab.haolap.range;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.strategy.PageSizer;
import cn.edu.neu.cloudlab.haolap.strategy.PageSizer10MStrategy;
import cn.edu.neu.cloudlab.haolap.util.DimensionHelper;
import cn.edu.neu.cloudlab.haolap.util.PageHelper;
import cn.edu.neu.cloudlab.haolap.util.Serializer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RangeForPage implements Range {
    private String cubeIdentifier;
    private long start;
    private long end;

    public RangeForPage(String cubeIdentifier, long start, long end) {
        super();
        this.cubeIdentifier = cubeIdentifier;
        this.start = start;
        this.end = end;
    }

    public RangeForPage(String cubeIdentifier, List<Long> startPointForElement,
                        List<Long> endPointForElement) throws CubeNotExistsException,
            SchemaNotExistsException {
        super();
        this.cubeIdentifier = cubeIdentifier;
        long lengths[] = DimensionHelper.getDimensionLengths(cubeIdentifier);

        BigInteger startForElement = Serializer.serialize(lengths,
                startPointForElement);
        BigInteger endForElement = Serializer.serialize(lengths,
                endPointForElement);

        this.start = PageHelper.belongTo(startForElement, cubeIdentifier);
        this.end = PageHelper.belongTo(endForElement, cubeIdentifier);
    }

    @Override
    public List<Long> getStartPoint() throws SchemaNotExistsException,
            CubeNotExistsException {
        PageSizer pageSizer = new PageSizer(new PageSizer10MStrategy());
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        Schema schema = schemaClient.getSchema(this.cubeIdentifier);
        long numberOfPageSegmentsInDimensions[] = pageSizer
                .getNumberOfPageSegmentsInDimensions(schema);
        long point[] = Serializer.deserialize(numberOfPageSegmentsInDimensions,
                new BigInteger(String.valueOf(start)));
        List<Long> startPoint = new ArrayList<Long>();
        for (int i = 0; i < point.length; i += 1) {
            startPoint.add(point[i]);
        }
        return startPoint;
    }

    @Override
    public List<Long> getEndPoint() throws SchemaNotExistsException,
            CubeNotExistsException {
        PageSizer pageSizer = new PageSizer(new PageSizer10MStrategy());
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        Schema schema = schemaClient.getSchema(this.cubeIdentifier);
        long numberOfPageSegmentsInDimensions[] = pageSizer
                .getNumberOfPageSegmentsInDimensions(schema);
        long point[] = Serializer.deserialize(numberOfPageSegmentsInDimensions,
                new BigInteger(String.valueOf(end)));
        List<Long> endPoint = new ArrayList<Long>();
        for (int i = 0; i < point.length; i += 1) {
            endPoint.add(point[i]);
        }
        return endPoint;
    }
}
