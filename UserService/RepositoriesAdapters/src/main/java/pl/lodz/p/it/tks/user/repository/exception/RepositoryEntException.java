package pl.lodz.p.it.tks.user.repository.exception;

public class RepositoryEntException extends Exception {
    public RepositoryEntException() {
        super();
    }

    public RepositoryEntException(String message) {
        super(message);
    }

    public RepositoryEntException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryEntException(Throwable cause) {
        super(cause);
    }
}
