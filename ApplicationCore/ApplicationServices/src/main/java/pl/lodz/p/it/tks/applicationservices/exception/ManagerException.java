package pl.lodz.p.it.tks.applicationservices.exception;

public class ManagerException extends RuntimeException {
    public ManagerException() {
        super();
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }
}
