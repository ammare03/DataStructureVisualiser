package exceptions;

public class StackUnderflowException extends RuntimeException {
    public StackUnderflowException() {
    }

    public StackUnderflowException(String message) {
        super(message);
    }

    public StackUnderflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public StackUnderflowException(Throwable cause) {
        super(cause);
    }

    public StackUnderflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
