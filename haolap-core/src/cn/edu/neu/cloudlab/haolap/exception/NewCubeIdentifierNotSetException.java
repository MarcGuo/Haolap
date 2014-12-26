package cn.edu.neu.cloudlab.haolap.exception;

public class NewCubeIdentifierNotSetException extends CubeException {

    private static final long serialVersionUID = -7780937975178538232L;

    public NewCubeIdentifierNotSetException() {
        super();
    }

    public NewCubeIdentifierNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewCubeIdentifierNotSetException(String message) {
        super(message);
    }

    public NewCubeIdentifierNotSetException(Throwable cause) {
        super(cause);
    }

}
