package net.catharos.groups;

import net.catharos.lib.core.lang.ArgumentRuntimeException;

/**
 * Represents a InactiveException
 */
public class InactiveException extends ArgumentRuntimeException {

    public InactiveException() {
    }

    public InactiveException(Inactivatable inactive) {
        this(inactive + " is inactive!");
    }

    public InactiveException(String message, Object... args) {
        super(message, args);
    }

    public InactiveException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public InactiveException(Throwable cause) {
        super(cause);
    }
}
