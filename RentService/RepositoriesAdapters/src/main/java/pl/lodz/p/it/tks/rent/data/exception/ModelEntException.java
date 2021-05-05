package pl.lodz.p.it.tks.rent.data.exception;

public class ModelEntException extends Exception {
    public ModelEntException() {
        super();
    }

    public ModelEntException(String message) {
        super(message);
    }

    public ModelEntException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelEntException(Throwable cause) {
        super(cause);
    }
}
