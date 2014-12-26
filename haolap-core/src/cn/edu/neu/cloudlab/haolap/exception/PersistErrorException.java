package cn.edu.neu.cloudlab.haolap.exception;

public class PersistErrorException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = 4447569791720147066L;

    public PersistErrorException() {
        super();
    }

    public PersistErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistErrorException(String message) {
        super(message);
    }

    public PersistErrorException(Throwable cause) {
        super(cause);
    }

}
