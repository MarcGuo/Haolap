package cn.edu.neu.cloudlab.haolap.strategy;

import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Schema;

import java.util.Set;

public class PageSizerDefaultStrategy implements PageSizerStrategy {

    Cutter cutter = new Cutter();

    public PageSizerDefaultStrategy() {
        super();
    }

    @Override
    public long[] getPageLengths(Schema schema) {
        Set<Dimension> dimensions = schema.getDimensions();
        int numberOfDimensions = dimensions.size();
        long pageLengths[] = new long[numberOfDimensions];
        int i = 0;
        for (Dimension dimension : dimensions) {
            long dimensionLength = dimension.getDimensionLength();
            long numberOfSegments = this.cutter
                    .getNumberOfPageSegmentsInDimension();
            pageLengths[i++] = (dimensionLength / numberOfSegments)
                    + (dimensionLength % numberOfSegments == 0 ? 0 : 1);
        }
        return pageLengths;
    }

    @Override
    public long[] getNumberOfPageSegmentsInDimensions(Schema schema) {
        Set<Dimension> dimensions = schema.getDimensions();
        int numberOfDimensions = dimensions.size();
        long[] numberOfPageSegmentsInDimensions = new long[numberOfDimensions];
        for (int i = 0; i < numberOfDimensions; i += 1) {
            numberOfPageSegmentsInDimensions[i] = this.cutter
                    .getNumberOfPageSegmentsInDimension();
        }
        return numberOfPageSegmentsInDimensions;
    }

    private class Cutter {
        private final long numberOfPageSegmentsInDimension = 7;

        public long getNumberOfPageSegmentsInDimension() {
            return numberOfPageSegmentsInDimension;
        }
    }

}
