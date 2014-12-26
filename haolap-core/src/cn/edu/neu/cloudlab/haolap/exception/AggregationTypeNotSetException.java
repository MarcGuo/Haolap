package cn.edu.neu.cloudlab.haolap.exception;

public class AggregationTypeNotSetException extends CubeException {

    private static final long serialVersionUID = 7411202248140504036L;

    public AggregationTypeNotSetException() {
        super();
    }

    public AggregationTypeNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public AggregationTypeNotSetException(String message) {
        super(message);
    }

    public AggregationTypeNotSetException(Throwable cause) {
        super(cause);
    }

}
