package cn.edu.neu.cloudlab.haolap.exception;

public class CorrespondingLevelNotExistsException extends CubeException {

    private static final long serialVersionUID = -6421422183851685116L;

    public CorrespondingLevelNotExistsException() {
        super();
    }

    public CorrespondingLevelNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CorrespondingLevelNotExistsException(String message) {
        super(message);
    }

    public CorrespondingLevelNotExistsException(Throwable cause) {
        super(cause);
    }

}
