package org.societies.groups.setting.subject;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.catharos.lib.core.util.CastSafe;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

/**
 * Represents a DefaultSubject
 */
public abstract class DefaultSubject extends AbstractSubject {
    private final Table<Setting, Target, Object> settings = HashBasedTable.create();

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
    public Table<Setting, Target, Object> getSettings() {
        return settings;
    }
}
