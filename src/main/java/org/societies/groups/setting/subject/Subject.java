package org.societies.groups.setting.subject;

import com.google.common.collect.Table;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

import java.util.UUID;

/**
 *
 */
public interface Subject {

    UUID getUUID();

    <V> void set(Setting<V> setting, Target target, V value);

    <V> void set(Setting<V> setting, V value);

    <V> void remove(Setting<V> setting, Target target);

    <V> void remove(Setting<V> setting);

    <V> V get(Setting<V> setting, Target target);

    boolean getBoolean(Setting<Boolean> setting, Target target);

    int getInteger(Setting<Integer> setting, Target target);

    double getDouble(Setting<Double> setting, Target target);

    <V> V get(Setting<V> setting);

    double getDouble(Setting<Double> setting);

    boolean getBoolean(Setting<Boolean> setting);

    int getInteger(Setting<Integer> setting);

    Table<Setting, Target, Object> getSettings();
}
