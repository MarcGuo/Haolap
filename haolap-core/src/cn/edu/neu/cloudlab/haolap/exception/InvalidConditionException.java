package cn.edu.neu.cloudlab.haolap.exception;

public class InvalidConditionException extends CubeException {

    private static final long serialVersionUID = 2999226712419381527L;

    public InvalidConditionException() {
        super();
    }

    public InvalidConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConditionException(String message) {
        super(message);
    }

    public InvalidConditionException(Throwable cause) {
        super(cause);
    }
}
