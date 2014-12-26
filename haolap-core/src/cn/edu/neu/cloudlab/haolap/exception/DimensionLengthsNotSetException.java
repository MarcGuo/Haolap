package cn.edu.neu.cloudlab.haolap.exception;

public class DimensionLengthsNotSetException extends CubeException {

    private static final long serialVersionUID = 6797964844181903022L;

    public DimensionLengthsNotSetException() {
        super();
    }

    public DimensionLengthsNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public DimensionLengthsNotSetException(String message) {
        super(message);
    }

    public DimensionLengthsNotSetException(Throwable cause) {
        super(cause);
    }

}
