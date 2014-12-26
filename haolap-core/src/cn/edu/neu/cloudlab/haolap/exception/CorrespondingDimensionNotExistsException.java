package cn.edu.neu.cloudlab.haolap.exception;

public class CorrespondingDimensionNotExistsException extends CubeException {

    private static final long serialVersionUID = 4249729819429478292L;

    public CorrespondingDimensionNotExistsException() {
        super();
    }

    public CorrespondingDimensionNotExistsException(String message,
                                                    Throwable cause) {
        super(message, cause);
    }

    public CorrespondingDimensionNotExistsException(String message) {
        super(message);
    }

    public CorrespondingDimensionNotExistsException(Throwable cause) {
        super(cause);
    }

}
