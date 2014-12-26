package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.AxisWithoutMeaningErrorException;

public interface AxisMapper {
    public String getAxisWithMeaning(long axisWithoutMeaning[])
            throws AxisWithoutMeaningErrorException;
}
