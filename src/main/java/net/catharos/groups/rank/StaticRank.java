package net.catharos.groups.rank;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.catharos.groups.Group;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a StaticAbstractRank
 */
public class StaticRank extends AbstractRank {

    public StaticRank(UUID uuid, String name, int priority, @Nullable Group group) {
        super(uuid, name, priority, group);
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public void setState(int state) {

    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {

    }

    @Override
    public <V> void set(Setting<V> setting, V value) {

    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {

    }

    @Override
    public <V> void remove(Setting<V> setting) {

    }

    @Override
    public <V> V get(Setting<V> setting, Target target) {
        return null;
    }

    @Override
    public <V> V get(Setting<V> setting) {
        return null;
    }

    @Override
    public Table<Setting, Target, Object> getSettings() {
        return HashBasedTable.create();
    }
}
