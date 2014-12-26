package cn.edu.neu.cloudlab.haolap.exception;

public class XmlPathNotFoundException extends CubeException {

    /**
     *
     */
    private static final long serialVersionUID = 8683424622595930015L;

    public XmlPathNotFoundException() {
        super();
    }

    public XmlPathNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlPathNotFoundException(String message) {
        super(message);
    }

    public XmlPathNotFoundException(Throwable cause) {
        super(cause);
    }

}
