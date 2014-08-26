package net.catharos.groups.setting.subject;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.lib.core.util.CastSafe;

/**
 * Represents a PermissionBase
 */
public abstract class AbstractSubject implements Subject {

    private final Table<Setting, Target, Object> permissions = HashBasedTable.create();

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        permissions.put(setting, target, value);
    }

    @Override
    public <V> V get(Setting<V> setting, Target target) {
        return CastSafe.toGeneric(permissions.get(setting, target));
    }

    @Override
    public <V> void set(Setting<V> setting, V value) {
        set(setting, Target.NO_TARGET, value);
    }

    @Override
    public <V> V get(Setting<V> setting) {
        return get(setting, Target.NO_TARGET);
    }
}
