package exceptions;

public class UnderflowException extends Exception {
    public UnderflowException() {
    }

    public UnderflowException(String message) {
        super(message);
    }

    public UnderflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnderflowException(Throwable cause) {
        super(cause);
    }

    public UnderflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
