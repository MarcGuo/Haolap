package cn.edu.neu.cloudlab.haolap.exception;

public class CubeNotExistsException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = -6846057904857969095L;

    public CubeNotExistsException() {
        super();
    }

    public CubeNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CubeNotExistsException(String message) {
        super(message);
    }

    public CubeNotExistsException(Throwable cause) {
        super(cause);
    }

}
