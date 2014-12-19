package org.societies.groups.rank.memory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.Nullable;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupPublisher;
import org.societies.groups.rank.DefaultRank;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

import javax.inject.Provider;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a MemoryRank
 */
public class MemoryRank extends DefaultRank {

    private Group group;

    private final GroupPublisher groupPublisher;

    @AssistedInject
    public MemoryRank(Provider<UUID> uuid,
                      @Assisted String name,
                      @Assisted int priority,
                      @Assisted @Nullable Group group,
                      Map<String, Setting<Boolean>> rules, GroupPublisher groupPublisher) {
        this(uuid.get(), name, priority, group, rules, groupPublisher);
    }

    @AssistedInject
    public MemoryRank(@Assisted UUID uuid,
                      @Assisted String name,
                      @Assisted int priority,
                      @Assisted @Nullable Group group,
                      Map<String, Setting<Boolean>> rules, GroupPublisher groupPublisher) {
        super(uuid, name, priority, rules);
        this.groupPublisher = groupPublisher;
        this.group = group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (linked()) {
            groupPublisher.publish(group);
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (linked()) {
            groupPublisher.publish(group);
        }
    }
}
