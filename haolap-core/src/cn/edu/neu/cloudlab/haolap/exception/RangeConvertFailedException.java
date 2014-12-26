package cn.edu.neu.cloudlab.haolap.exception;

/**
 * the exception will be thrown when the convert failed
 *
 * @author Neoh
 */
public class RangeConvertFailedException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = -663474796559269095L;

    public RangeConvertFailedException() {
        super();
    }

    public RangeConvertFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RangeConvertFailedException(String message) {
        super(message);
    }

    public RangeConvertFailedException(Throwable cause) {
        super(cause);
    }

}
