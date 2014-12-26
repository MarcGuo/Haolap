package cn.edu.neu.cloudlab.haolap.exception;

public class PageFullException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = -7034921776691928914L;

    public PageFullException() {
        super();
    }

    public PageFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageFullException(String message) {
        super(message);
    }

    public PageFullException(Throwable cause) {
        super(cause);
    }

}
