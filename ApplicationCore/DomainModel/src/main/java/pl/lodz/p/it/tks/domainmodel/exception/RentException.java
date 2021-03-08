package pl.lodz.p.it.tks.domainmodel.exception;

public class RentException extends ModelException {
    public RentException() {
        super();
    }

    public RentException(String message) {
        super(message);
    }

    public RentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentException(Throwable cause) {
        super(cause);
    }
}
