package cn.edu.neu.cloudlab.haolap.exception;

public class AxisWithoutMeaningErrorException extends CubeException {

    private static final long serialVersionUID = -7977283834764679675L;

    public AxisWithoutMeaningErrorException() {
        super();
    }

    public AxisWithoutMeaningErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AxisWithoutMeaningErrorException(String message) {
        super(message);
    }

    public AxisWithoutMeaningErrorException(Throwable cause) {
        super(cause);
    }

}
