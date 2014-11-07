package net.catharos.groups.setting;

import net.catharos.lib.core.lang.ArgumentException;

/**
 * Represents a SettingException
 */
public class SettingException extends ArgumentException {

    public SettingException() {
    }

    public SettingException(String message, Object... args) {
        super(message, args);
    }

    public SettingException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public SettingException(Throwable cause) {
        super(cause);
    }
}
