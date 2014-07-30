package net.catharos.groups.setting.subject;

import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;

import java.util.UUID;

/**
 *
 */
public interface Subject extends Target {

    @Override
    UUID getUUID();

    void set(Setting setting, Target target, SettingValue value);

    void set(Setting setting, SettingValue value);


    SettingValue get(Setting setting, Target target);

    SettingValue get(Setting setting);
}
