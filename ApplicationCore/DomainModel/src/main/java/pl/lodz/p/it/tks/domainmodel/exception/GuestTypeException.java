package pl.lodz.p.it.tks.domainmodel.exception;

public class GuestTypeException extends ModelException {
    public GuestTypeException() {
        super();
    }

    public GuestTypeException(String message) {
        super(message);
    }

    public GuestTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestTypeException(Throwable cause) {
        super(cause);
    }
}
