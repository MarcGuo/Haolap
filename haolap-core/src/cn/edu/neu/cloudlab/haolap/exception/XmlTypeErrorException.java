package cn.edu.neu.cloudlab.haolap.exception;

public class XmlTypeErrorException extends CubeException {

    private static final long serialVersionUID = 2969430384699770681L;

    public XmlTypeErrorException() {
        super();
    }

    public XmlTypeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlTypeErrorException(String message) {
        super(message);
    }

    public XmlTypeErrorException(Throwable cause) {
        super(cause);
    }

}
