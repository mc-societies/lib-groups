package net.catharos.groups.validate;

/**
 * Represents a ValidateResult
 */
public class ValidateResult {

    private final String message;
    private final boolean success;

    public ValidateResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailed() {
        return !success;
    }

    public String getMessage() {
        return message;
    }
}
