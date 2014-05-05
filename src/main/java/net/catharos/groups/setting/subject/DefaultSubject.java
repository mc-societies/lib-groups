package net.catharos.groups.setting.subject;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;

/**
 * Represents a PermissionBase
 */
public class DefaultSubject implements Subject {

    private final Table<Setting, Target, SettingValue> permissions = HashBasedTable.create();

    @Override
    public void set(Setting setting, Target target, SettingValue value) {
        permissions.put(setting, target, value);
    }

    @Override
    public SettingValue get(Setting setting, Target target) {
        return permissions.get(setting, target);
    }
}
