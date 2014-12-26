package cn.edu.neu.cloudlab.haolap.exception;

public class NumberOfElementsPerPageNotSetException extends CubeException {

    private static final long serialVersionUID = -6255155549129740389L;

    public NumberOfElementsPerPageNotSetException() {
        super();
    }

    public NumberOfElementsPerPageNotSetException(String message,
                                                  Throwable cause) {
        super(message, cause);
    }

    public NumberOfElementsPerPageNotSetException(String message) {
        super(message);
    }

    public NumberOfElementsPerPageNotSetException(Throwable cause) {
        super(cause);
    }

}
