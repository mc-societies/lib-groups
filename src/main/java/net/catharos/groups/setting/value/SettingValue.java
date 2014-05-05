package net.catharos.groups.setting.value;

import net.catharos.groups.setting.Setting;

/**
 * Represents a PermissionValue
 */
public interface SettingValue {

    Setting getSetting();

    boolean booleanValue();
}
