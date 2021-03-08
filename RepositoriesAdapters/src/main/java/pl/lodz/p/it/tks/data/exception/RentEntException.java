package pl.lodz.p.it.tks.data.exception;

public class RentEntException extends ModelEntException {
    public RentEntException() {
        super();
    }

    public RentEntException(String message) {
        super(message);
    }

    public RentEntException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentEntException(Throwable cause) {
        super(cause);
    }
}
