package pl.lodz.p.it.tks.data.exception;

public class GuestEntException extends EntityException {
    public GuestEntException() {
        super();
    }

    public GuestEntException(String message) {
        super(message);
    }

    public GuestEntException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestEntException(Throwable cause) {
        super(cause);
    }
}
