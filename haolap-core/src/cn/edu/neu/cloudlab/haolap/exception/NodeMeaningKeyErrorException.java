package cn.edu.neu.cloudlab.haolap.exception;

public class NodeMeaningKeyErrorException extends CubeException {

    private static final long serialVersionUID = 1844199581522318458L;

    public NodeMeaningKeyErrorException() {
        super();
    }

    public NodeMeaningKeyErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeMeaningKeyErrorException(String message) {
        super(message);
    }

    public NodeMeaningKeyErrorException(Throwable cause) {
        super(cause);
    }

}
