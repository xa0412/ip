package exception;

/**
 * Represents a custom exception for validation errors in task inputs.
 */
public class XanException extends IllegalArgumentException {
    public XanException(String message) {
        super(message);
    }
}
