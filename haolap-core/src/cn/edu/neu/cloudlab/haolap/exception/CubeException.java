package cn.edu.neu.cloudlab.haolap.exception;

/**
 * This class defines the base Exception class in this program
 *
 * @author Neoh
 */
public class CubeException extends Exception {

    private static final long serialVersionUID = -6497117117698513050L;

    public CubeException() {
    }

    public CubeException(String message) {
        super(message);
    }

    public CubeException(Throwable cause) {
        super(cause);
    }

    public CubeException(String message, Throwable cause) {
        super(message, cause);
    }
}
