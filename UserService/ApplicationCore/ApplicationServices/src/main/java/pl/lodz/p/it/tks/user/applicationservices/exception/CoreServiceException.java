package pl.lodz.p.it.tks.user.applicationservices.exception;

public class CoreServiceException extends Exception {
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
