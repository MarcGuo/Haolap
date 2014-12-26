package cn.edu.neu.cloudlab.haolap.range;

import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.DimensionHelper;
import cn.edu.neu.cloudlab.haolap.util.Serializer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RangeForCubeElement implements Range {
    private String cubeIdentifier;
    private BigInteger start;
    private BigInteger end;

    public RangeForCubeElement(String cubeIdentifier, BigInteger start, BigInteger end) {
        super();
        this.cubeIdentifier = cubeIdentifier;
        this.start = start;
        this.end = end;
    }

    public RangeForCubeElement(String cubeIdentifier, List<Long> startPoint,
                               List<Long> endPoint) throws CubeNotExistsException,
            SchemaNotExistsException {
        super();
        this.cubeIdentifier = cubeIdentifier;

        long lengths[] = DimensionHelper
                .getDimensionLengths(this.cubeIdentifier);

        start = Serializer.serialize(lengths, startPoint);
        end = Serializer.serialize(lengths, endPoint);
    }

    @Override
    public List<Long> getStartPoint() throws CubeNotExistsException,
            SchemaNotExistsException {
        long lengths[] = DimensionHelper
                .getDimensionLengths(this.cubeIdentifier);

        long point[] = Serializer.deserialize(lengths, start);
        List<Long> startPoint = new ArrayList<Long>();
        for (int i = 0; i < point.length; i += 1) {
            startPoint.add(point[i]);
        }

        return startPoint;
    }

    @Override
    public List<Long> getEndPoint() throws CubeNotExistsException,
            SchemaNotExistsException {
        long lengths[] = DimensionHelper
                .getDimensionLengths(this.cubeIdentifier);

        long point[] = Serializer.deserialize(lengths, end);
        List<Long> endPoint = new ArrayList<Long>();
        for (int i = 0; i < point.length; i += 1) {
            endPoint.add(point[i]);
        }

        return endPoint;
    }
}
