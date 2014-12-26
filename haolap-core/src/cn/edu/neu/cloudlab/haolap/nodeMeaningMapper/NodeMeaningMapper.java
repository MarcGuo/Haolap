package cn.edu.neu.cloudlab.haolap.nodeMeaningMapper;

import cn.edu.neu.cloudlab.haolap.exception.NodeMeaningKeyErrorException;

public interface NodeMeaningMapper {
    public String getMeaning(String key) throws NumberFormatException,
            NodeMeaningKeyErrorException;
}
