package cash.exceptions;

public class DBException extends RuntimeException {
    private static final long serialVersionUID = -3705622322151650021L;

    public DBException(String message, Exception cause) {
        super(message, cause);
    }
}
