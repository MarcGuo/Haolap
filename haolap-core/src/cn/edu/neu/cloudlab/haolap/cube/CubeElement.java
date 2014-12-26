package cn.edu.neu.cloudlab.haolap.cube;

public class CubeElement<T> implements CubeElementInterface<T> {

    // we code them in the ordering the same as array and start from 0
    private long position;
    private T data;

    public CubeElement(long position, T data) {
        super();
        this.position = position;
        this.data = data;
    }

    @Override
    public long getPosition() {
        return this.position;
    }

    @Override
    public T getData() {
        return this.data;
    }

}
