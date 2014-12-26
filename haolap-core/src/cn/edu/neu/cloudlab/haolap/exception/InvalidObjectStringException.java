package cn.edu.neu.cloudlab.haolap.exception;

public class InvalidObjectStringException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = -7893976348730827120L;

    public InvalidObjectStringException() {
        super();
    }

    public InvalidObjectStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidObjectStringException(String message) {
        super(message);
    }

    public InvalidObjectStringException(Throwable cause) {
        super(cause);
    }

}
