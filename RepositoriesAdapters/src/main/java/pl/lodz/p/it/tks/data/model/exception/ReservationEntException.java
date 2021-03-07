package pl.lodz.p.it.tks.data.model.exception;

public class ReservationEntException extends EntityException {
    public ReservationEntException() {
        super();
    }

    public ReservationEntException(String message) {
        super(message);
    }

    public ReservationEntException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationEntException(Throwable cause) {
        super(cause);
    }
}
