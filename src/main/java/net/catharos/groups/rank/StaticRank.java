package net.catharos.groups.rank;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.assistedinject.Assisted;
import net.catharos.groups.Group;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

/**
 * Represents a StaticAbstractRank
 */
public class StaticRank extends AbstractRank {

    public StaticRank(@Assisted UUID uuid, @Assisted String name, @Assisted int priority, Map<String, Setting<Boolean>> rules) {
        super(uuid, name, priority, rules);
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public void setState(int state) {

    }

    @Nullable
    @Override
    public Group getGroup() {
        return null;
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
