package cn.edu.neu.cloudlab.haolap.exception;

public class SchemaNotSetException extends CubeException {

    private static final long serialVersionUID = 6952025333479040663L;

    public SchemaNotSetException() {
        super();
    }

    public SchemaNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaNotSetException(String message) {
        super(message);
    }

    public SchemaNotSetException(Throwable cause) {
        super(cause);
    }

}
