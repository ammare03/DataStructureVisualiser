package exceptions;

public class StackOverflowException extends RuntimeException {
    public StackOverflowException() {
    }

    public StackOverflowException(String message) {
        super(message);
    }

    public StackOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public StackOverflowException(Throwable cause) {
        super(cause);
    }

    public StackOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
