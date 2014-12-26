package cn.edu.neu.cloudlab.haolap.exception;

public class AggregationTypeInvalidException extends CubeException {

    private static final long serialVersionUID = 5630787322962889473L;

    public AggregationTypeInvalidException() {
        super();
    }

    public AggregationTypeInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public AggregationTypeInvalidException(String message) {
        super(message);
    }

    public AggregationTypeInvalidException(Throwable cause) {
        super(cause);
    }

}
