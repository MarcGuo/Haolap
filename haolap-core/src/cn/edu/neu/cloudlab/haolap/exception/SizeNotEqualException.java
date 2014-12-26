package cn.edu.neu.cloudlab.haolap.exception;

public class SizeNotEqualException extends CubeException {

    private static final long serialVersionUID = -2559341909095174368L;

    public SizeNotEqualException() {
        super();
    }

    public SizeNotEqualException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeNotEqualException(String message) {
        super(message);
    }

    public SizeNotEqualException(Throwable cause) {
        super(cause);
    }

}
