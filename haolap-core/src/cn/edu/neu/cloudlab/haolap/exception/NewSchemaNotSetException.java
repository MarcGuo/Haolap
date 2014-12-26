package cn.edu.neu.cloudlab.haolap.exception;

public class NewSchemaNotSetException extends CubeException {

    private static final long serialVersionUID = 6952025333479040663L;

    public NewSchemaNotSetException() {
        super();
    }

    public NewSchemaNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewSchemaNotSetException(String message) {
        super(message);
    }

    public NewSchemaNotSetException(Throwable cause) {
        super(cause);
    }

}
