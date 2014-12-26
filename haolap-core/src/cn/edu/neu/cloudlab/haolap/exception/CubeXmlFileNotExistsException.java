package cn.edu.neu.cloudlab.haolap.exception;

public class CubeXmlFileNotExistsException extends CubeException {

    private static final long serialVersionUID = 86893880218796475L;

    public CubeXmlFileNotExistsException() {
        super();
    }

    public CubeXmlFileNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CubeXmlFileNotExistsException(String message) {
        super(message);
    }

    public CubeXmlFileNotExistsException(Throwable cause) {
        super(cause);
    }

}
