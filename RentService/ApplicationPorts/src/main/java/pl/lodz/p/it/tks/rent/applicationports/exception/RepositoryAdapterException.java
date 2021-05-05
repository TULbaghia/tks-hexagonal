package pl.lodz.p.it.tks.rent.applicationports.exception;

public class RepositoryAdapterException extends Exception {
    public RepositoryAdapterException() {
        super();
    }

    public RepositoryAdapterException(String message) {
        super(message);
    }

    public RepositoryAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryAdapterException(Throwable cause) {
        super(cause);
    }

    protected RepositoryAdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
