package net.catharos.groups.setting.value;

import net.catharos.groups.setting.Setting;

/**
 * Represents a EmptyPermissionValue
 */
public class DefaultSettingValue implements SettingValue {

    public static final DefaultSettingValue EMPTY = new DefaultSettingValue(null, SettingState.FALSE);

    private final Setting setting;
    private final SettingState state;

    public DefaultSettingValue(Setting setting, SettingState state) {
        this.setting = setting;
        this.state = state;
    }

    @Override
    public Setting getSetting() {
        return setting;
    }

    @Override
    public boolean booleanValue() {
        return state.getValue();
    }
}
