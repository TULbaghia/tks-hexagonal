package pl.lodz.p.it.tks.domainmodel.exception;

public class ReservationException extends ModelException {
    public ReservationException() {
        super();
    }

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationException(Throwable cause) {
        super(cause);
    }
}
