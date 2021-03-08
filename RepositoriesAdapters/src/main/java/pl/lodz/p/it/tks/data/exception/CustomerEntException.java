package pl.lodz.p.it.tks.data.exception;

public class CustomerEntException extends ModelEntException {
    public CustomerEntException() {
        super();
    }

    public CustomerEntException(String message) {
        super(message);
    }

    public CustomerEntException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerEntException(Throwable cause) {
        super(cause);
    }
}
