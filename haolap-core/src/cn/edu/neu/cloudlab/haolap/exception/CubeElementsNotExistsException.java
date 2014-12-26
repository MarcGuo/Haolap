package cn.edu.neu.cloudlab.haolap.exception;

public class CubeElementsNotExistsException extends CubeException {

    private static final long serialVersionUID = 8056629993687064116L;

    public CubeElementsNotExistsException() {
        super();
    }

    public CubeElementsNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CubeElementsNotExistsException(String message) {
        super(message);
    }

    public CubeElementsNotExistsException(Throwable cause) {
        super(cause);
    }

}
