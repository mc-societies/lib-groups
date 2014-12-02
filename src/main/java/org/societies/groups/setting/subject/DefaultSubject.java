package org.societies.groups.setting.subject;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import net.catharos.lib.core.util.CastSafe;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

import java.util.UUID;

/**
 * Represents a DefaultSubject
 */
public class DefaultSubject extends AbstractSubject {
    private final Table<Setting, Target, Object> settings = HashBasedTable.create();

    private final UUID uuid;

    @Inject
    public DefaultSubject(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

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
