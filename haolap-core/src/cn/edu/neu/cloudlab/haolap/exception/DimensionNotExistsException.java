package cn.edu.neu.cloudlab.haolap.exception;

public class DimensionNotExistsException extends CubeException {

    private static final long serialVersionUID = -2842534701924172996L;

    public DimensionNotExistsException() {
        super();
    }

    public DimensionNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DimensionNotExistsException(String message) {
        super(message);
    }

    public DimensionNotExistsException(Throwable cause) {
        super(cause);
    }

}
