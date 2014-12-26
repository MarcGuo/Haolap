package cn.edu.neu.cloudlab.haolap.exception;

public class CubeAlreadyExistsException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = -5234088175669932857L;

    public CubeAlreadyExistsException() {
        super();
    }

    public CubeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CubeAlreadyExistsException(String message) {
        super(message);
    }

    public CubeAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
