package cn.edu.neu.cloudlab.dataloader;

import cn.edu.neu.cloudlab.haolap.exception.*;

import java.io.IOException;

public abstract class InitializationAbstract implements Initialization {

    private Initialization next;

    public InitializationAbstract(Initialization next) {
        super();
        this.next = next;
    }

    public InitializationAbstract() {
        super();
        this.next = null;
    }

    @Override
    public void setNext(Initialization next) {
        this.next = next;
    }

    @Override
    public void doInitialize() throws SchemaAlreadyExistsException,
            CubeAlreadyExistsException, PersistErrorException,
            PageFullException, IOException, SchemaNotExistsException,
            CubeNotExistsException {
        this.initialize();
        if (this.next != null) {
            this.next.doInitialize();
        }
    }

    protected abstract void initialize() throws SchemaAlreadyExistsException,
            CubeAlreadyExistsException, PersistErrorException,
            PageFullException, IOException, SchemaNotExistsException,
            CubeNotExistsException;
}
