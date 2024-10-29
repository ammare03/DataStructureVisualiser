package exceptions;

public class OverflowException extends Exception {
    public OverflowException() {
    }

    public OverflowException(String message) {
        super(message);
    }

    public OverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverflowException(Throwable cause) {
        super(cause);
    }

    public OverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
