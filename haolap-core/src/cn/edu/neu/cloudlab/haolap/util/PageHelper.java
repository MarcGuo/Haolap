package cn.edu.neu.cloudlab.haolap.util;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import cn.edu.neu.cloudlab.haolap.strategy.PageSizer;
import cn.edu.neu.cloudlab.haolap.strategy.PageSizer10MStrategy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PageHelper {
    private static PageSizer pageSizer = new PageSizer(
            new PageSizer10MStrategy());

    public static long[] getPageLengths(String cubeIdentifier)
            throws SchemaNotExistsException, CubeNotExistsException {
        Schema schema = getSchema(cubeIdentifier);
//		PageSizer pageSizer = new PageSizer(new PageSizerMaxBiggerStrategy());
        return pageSizer.getPageLengths(schema);
    }

    public static long[] getPageLengths(Schema schema) {
//		PageSizer pageSizer = new PageSizer(new PageSizerMaxBiggerStrategy());
        return pageSizer.getPageLengths(schema);
    }

    public static long getNumberOfPages(String cubeIdentifier)
            throws SchemaNotExistsException, CubeNotExistsException {
        Schema schema = getSchema(cubeIdentifier);
        long numberOfPageSegmentsInDimensions[] = pageSizer
                .getNumberOfPageSegmentsInDimensions(schema);
        long numberOfPages = 1;
        int numberOfDimensions = schema.getDimensions().size();
        for (int i = 0; i < numberOfDimensions; i += 1) {
            numberOfPages *= numberOfPageSegmentsInDimensions[i];
        }
        return numberOfPages;
    }

    public static long getNumberOfPages(Schema schema) {
        long numberOfPageSegmentsInDimensions[] = pageSizer
                .getNumberOfPageSegmentsInDimensions(schema);
        long numberOfPages = 1;
        int numberOfDimensions = schema.getDimensions().size();
        for (int i = 0; i < numberOfDimensions; i += 1) {
            numberOfPages *= numberOfPageSegmentsInDimensions[i];
        }
        return numberOfPages;
    }

    public static long belongTo(BigInteger elementNo, String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException {
        long dimensionLengths[] = DimensionHelper
                .getDimensionLengths(cubeIdentifier);
        long pageLengths[] = PageHelper.getPageLengths(cubeIdentifier);
        int numberOfDimensions = dimensionLengths.length;
        long elementPosition[] = Serializer.deserialize(dimensionLengths,
                elementNo);
        long pagePosition[] = new long[numberOfDimensions];
        for (int i = 0; i < numberOfDimensions; i += 1) {
            pagePosition[i] = elementPosition[i] / pageLengths[i];
        }
        Schema schema = getSchema(cubeIdentifier);
        long numberOfPageSegmentsInDimensions[] = pageSizer
                .getNumberOfPageSegmentsInDimensions(schema);
        BigInteger pageNo = Serializer.serialize(numberOfPageSegmentsInDimensions,
                pagePosition);
        return pageNo.longValue();
    }

    public static long belongTo(BigInteger elementNo, Schema schema) {
        long dimensionLengths[] = DimensionHelper.getDimensionLengths(schema);
        long pageLengths[] = PageHelper.getPageLengths(schema);
        int numberOfDimensions = dimensionLengths.length;
        long elementPosition[] = Serializer.deserialize(dimensionLengths,
                elementNo);
        long pagePosition[] = new long[numberOfDimensions];
        for (int i = 0; i < numberOfDimensions; i += 1) {
            pagePosition[i] = elementPosition[i] / pageLengths[i];
        }
        long numberOfPageSegmentsInDimensions[] = pageSizer
                .getNumberOfPageSegmentsInDimensions(schema);

        BigInteger pageNo = Serializer.serialize(numberOfPageSegmentsInDimensions,
                pagePosition);
        return pageNo.longValue();
    }

    public static List<Long> translateElementPointToPagePoint(
            List<Long> elementPoint, Schema schema) {
        List<Long> pagePoint = new ArrayList<Long>();
        long pageLengths[] = getPageLengths(schema);
        int i = 0;
        for (long l : elementPoint) {
            pagePoint.add(l / pageLengths[i]);
            i += 1;
        }
        return pagePoint;
    }

    public static long[] getNumberOfPageSegmentsInDimensions(Schema schema) {
        return pageSizer.getNumberOfPageSegmentsInDimensions(schema);
    }

    private static Schema getSchema(String cubeIdentifier)
            throws SchemaNotExistsException, CubeNotExistsException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        return schemaClient.getSchema(cubeIdentifier);
    }
}
