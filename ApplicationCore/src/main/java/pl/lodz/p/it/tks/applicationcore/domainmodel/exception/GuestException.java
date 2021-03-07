package pl.lodz.p.it.tks.applicationcore.domainmodel.exception;

public class GuestException extends ModelException {
    public GuestException() {
        super();
    }

    public GuestException(String message) {
        super(message);
    }

    public GuestException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestException(Throwable cause) {
        super(cause);
    }
}
