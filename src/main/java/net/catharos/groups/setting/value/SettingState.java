package net.catharos.groups.setting.value;

/**
* Represents a SettingState
*/
public enum SettingState {
    TRUE(true),
    FALSE(false),
    UNSET(false);

    private final boolean value;

    SettingState(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
