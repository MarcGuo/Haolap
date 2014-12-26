package cn.edu.neu.cloudlab.haolap.exception;

public class CubeIdentifierNotSetException extends CubeException {

    private static final long serialVersionUID = 4887354374500525590L;

    public CubeIdentifierNotSetException() {
        super();
    }

    public CubeIdentifierNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public CubeIdentifierNotSetException(String message) {
        super(message);
    }

    public CubeIdentifierNotSetException(Throwable cause) {
        super(cause);
    }

}
