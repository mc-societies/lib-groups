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

    protected final Table<Setting, Target, Object> settings = HashBasedTable.create();

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        settings.put(setting, target, value);
    }

    @Override
    public <V> V get(Setting<V> setting, Target target) {
        Object obj = settings.get(setting, target);
        if (obj == null) {
            return null;
        }
        return CastSafe.toGeneric(obj);
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        settings.remove(setting, target);
    }

    @Override
    public <V> void remove(Setting<V> setting) {
        remove(setting, this);
    }

    @Override
    public <V> void set(Setting<V> setting, V value) {
        set(setting, this, value);
    }

    @Override
    public <V> V get(Setting<V> setting) {
        return get(setting, this);
    }

    @Override
    public Table<Setting, Target, Object> getSettings() {
        return settings;
    }
}
