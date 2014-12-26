package cn.edu.neu.cloudlab.haolap.exception;

public class InvalidPointException extends CubeException {

    private static final long serialVersionUID = 6213147451892623726L;

    public InvalidPointException() {
        super();
    }

    public InvalidPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPointException(String message) {
        super(message);
    }

    public InvalidPointException(Throwable cause) {
        super(cause);
    }
}
