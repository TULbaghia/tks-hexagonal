package pl.lodz.p.it.tks.user.soap.exception;

public class SoapException extends RuntimeException {
    public SoapException() {
        super();
    }

    public SoapException(String message) {
        super(message);
    }

    public SoapException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoapException(Throwable cause) {
        super(cause);
    }
}
