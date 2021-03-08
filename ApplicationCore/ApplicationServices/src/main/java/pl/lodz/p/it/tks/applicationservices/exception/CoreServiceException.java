package pl.lodz.p.it.tks.applicationservices.exception;

public class CoreServiceException extends RuntimeException {
    public CoreServiceException() {
        super();
    }

    public CoreServiceException(String message) {
        super(message);
    }

    public CoreServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreServiceException(Throwable cause) {
        super(cause);
    }
}
