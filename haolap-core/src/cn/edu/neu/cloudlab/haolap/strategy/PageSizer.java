package cn.edu.neu.cloudlab.haolap.strategy;

import cn.edu.neu.cloudlab.haolap.cube.Schema;

public class PageSizer {
    PageSizerStrategy strategy;

    public PageSizer(PageSizerStrategy strategy) {
        super();
        this.strategy = strategy;
    }

    public long[] getPageLengths(Schema schema) {
        return this.strategy.getPageLengths(schema);
    }

    public long[] getNumberOfPageSegmentsInDimensions(Schema schema) {
        return this.strategy.getNumberOfPageSegmentsInDimensions(schema);
    }
}
