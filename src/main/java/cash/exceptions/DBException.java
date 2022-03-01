package cash.exceptions;

public class DBException extends RuntimeException {

    public DBException(String message, Exception cause) {
        super(message, cause);
    }
}
