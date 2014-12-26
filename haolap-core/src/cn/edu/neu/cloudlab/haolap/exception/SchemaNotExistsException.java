package cn.edu.neu.cloudlab.haolap.exception;

public class SchemaNotExistsException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = 3858113296010396887L;

    public SchemaNotExistsException() {
        super();
    }

    public SchemaNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaNotExistsException(String message) {
        super(message);
    }

    public SchemaNotExistsException(Throwable cause) {
        super(cause);
    }

}
