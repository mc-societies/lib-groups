package net.catharos.groups.setting.subject;

import com.google.common.collect.Table;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;

import java.util.UUID;

/**
 *
 */
public interface Subject extends Target {

    @Override
    UUID getUUID();

    <V> void set(Setting<V> setting, Target target, V value);

    <V> void set(Setting<V> setting, V value);

    <V> void remove(Setting<V> setting, Target target);

    <V> void remove(Setting<V> setting);


    <V> V get(Setting<V> setting, Target target);

    <V> V get(Setting<V> setting);

    Table<Setting, Target, Object> getSettings();
}
