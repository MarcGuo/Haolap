package cn.edu.neu.cloudlab.haolap.cube;

/**
 * the Interface for cube
 *
 * @param <T> typeOfData
 * @author Neoh
 */
public interface CubeElementInterface<T> {
    /**
     * get the position of the element in the cube
     *
     * @return position
     */
    long getPosition();

    /**
     * get the data in the element
     *
     * @return
     */
    T getData();
}
