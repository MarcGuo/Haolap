package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.exception.*;

import java.io.IOException;

public interface Initialization {
    public void doInitialize() throws SchemaAlreadyExistsException,
            CubeAlreadyExistsException, PersistErrorException,
            PageFullException, IOException, SchemaNotExistsException,
            CubeNotExistsException;

    public void setNext(Initialization next);
}
