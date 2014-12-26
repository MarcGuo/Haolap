package cn.edu.neu.cloudlab.haolap.exception;

public class CorrespondingSchemaNotExistsException extends CubeException {

    private static final long serialVersionUID = 762685819428633211L;

    public CorrespondingSchemaNotExistsException() {
        super();
    }

    public CorrespondingSchemaNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CorrespondingSchemaNotExistsException(String message) {
        super(message);
    }

    public CorrespondingSchemaNotExistsException(Throwable cause) {
        super(cause);
    }

}
