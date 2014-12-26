package cn.edu.neu.cloudlab.haolap.strategy;

import cn.edu.neu.cloudlab.haolap.cube.Schema;

public interface PageSizerStrategy {
    public long[] getPageLengths(Schema schema);

    public long[] getNumberOfPageSegmentsInDimensions(Schema schema);
}
