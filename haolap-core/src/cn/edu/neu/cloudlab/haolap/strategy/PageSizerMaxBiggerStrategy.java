package cn.edu.neu.cloudlab.haolap.strategy;

import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Schema;

import java.util.Set;

public class PageSizerMaxBiggerStrategy implements PageSizerStrategy {
    Cutter cutter = new Cutter();

    public PageSizerMaxBiggerStrategy() {
        super();
    }

    @Override
    public long[] getPageLengths(Schema schema) {
        Set<Dimension> dimensions = schema.getDimensions();
        int numberOfDimensions = dimensions.size();
        long pageLengths[] = new long[numberOfDimensions];
        long[] numberOfPageSegmentsInDimensions = this.cutter
                .getNumberOfPageSegmentsInDimension(schema);
        int i = 0;
        for (Dimension dimension : dimensions) {
            long dimensionLength = dimension.getDimensionLength();
            pageLengths[i] = (dimensionLength / numberOfPageSegmentsInDimensions[i])
                    + (dimensionLength % numberOfPageSegmentsInDimensions[i] == 0 ? 0
                    : 1);
            i += 1;
        }
        return pageLengths;
    }

    @Override
    public long[] getNumberOfPageSegmentsInDimensions(Schema schema) {
        long[] numberOfPageSegmentsInDimensions = this.cutter
                .getNumberOfPageSegmentsInDimension(schema);
        return numberOfPageSegmentsInDimensions;
    }

    private static class Cutter {
        private static final long size = (long) Math.pow(10240000000.0 * 1024.0 * 1024.0 / 28.0,
                1.0 / 3); // 10M

        public long[] getNumberOfPageSegmentsInDimension(Schema schema) {
            Set<Dimension> dimensions = schema.getDimensions();
            int numberOfDimensions = dimensions.size();
            long[] numberOfPageSegmentsInDimensions = new long[numberOfDimensions];
            int i = 0;
            for (Dimension dimension : dimensions) {
                numberOfPageSegmentsInDimensions[i] = dimension
                        .getDimensionLength() / size + (dimension
                        .getDimensionLength() % size == 0 ? 0 : 1);
                i += 1;
            }
            return numberOfPageSegmentsInDimensions;
        }
    }
}
