package net.catharos.groups.setting.subject;

import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;

/**
 *
 */
public interface Subject {

    void set(Setting setting, Target target, SettingValue value);

    SettingValue get(Setting setting, Target target);
}
