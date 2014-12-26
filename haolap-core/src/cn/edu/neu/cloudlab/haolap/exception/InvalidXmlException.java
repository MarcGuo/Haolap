package cn.edu.neu.cloudlab.haolap.exception;

public class InvalidXmlException extends CubeException {

    private static final long serialVersionUID = 4210681882746865011L;

    public InvalidXmlException() {
        super();
    }

    public InvalidXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidXmlException(String message) {
        super(message);
    }

    public InvalidXmlException(Throwable cause) {
        super(cause);
    }

}
