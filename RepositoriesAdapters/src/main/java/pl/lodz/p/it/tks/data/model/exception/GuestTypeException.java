package pl.lodz.p.it.tks.data.model.exception;

public class GuestTypeException extends EntityException{
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
