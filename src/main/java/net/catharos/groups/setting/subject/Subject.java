package net.catharos.groups.setting.subject;

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


    <V> V get(Setting<V> setting, Target target);

    <V> V get(Setting<V> setting);
}
