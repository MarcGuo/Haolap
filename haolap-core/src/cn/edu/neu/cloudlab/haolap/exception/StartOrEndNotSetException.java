package cn.edu.neu.cloudlab.haolap.exception;

public class StartOrEndNotSetException extends CubeException {

    private static final long serialVersionUID = 7655638287070808693L;

    public StartOrEndNotSetException() {
        super();
    }

    public StartOrEndNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public StartOrEndNotSetException(String message) {
        super(message);
    }

    public StartOrEndNotSetException(Throwable cause) {
        super(cause);
    }

}
