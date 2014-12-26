package cn.edu.neu.cloudlab.haolap.range;

import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.util.List;

public interface Range {
    List<Long> getStartPoint() throws CubeNotExistsException,
            SchemaNotExistsException;

    List<Long> getEndPoint() throws CubeNotExistsException,
            SchemaNotExistsException;
}
