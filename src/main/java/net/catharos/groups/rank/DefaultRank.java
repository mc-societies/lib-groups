package net.catharos.groups.rank;

import com.google.common.primitives.Ints;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.catharos.groups.DefaultGroup;
import net.catharos.groups.Group;
import net.catharos.groups.publisher.SettingPublisher;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractSubject;
import net.catharos.groups.setting.target.Target;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Provider;
import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends AbstractSubject implements Rank {

    private final UUID uuid;
    private final String name;
    private final int priority;
    private Group group;
    private final SettingPublisher settingPublisher;

    private boolean prepared = false;

    @AssistedInject
    public DefaultRank(Provider<UUID> uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       @Assisted @Nullable Group group,
                       SettingPublisher settingPublisher) {
        this(uuid.get(), name, priority, group, settingPublisher);
    }

    @AssistedInject
    public DefaultRank(@Assisted UUID uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       @Assisted @Nullable Group group,
                       SettingPublisher settingPublisher) {
        this.uuid = uuid;
        this.name = name;
        this.priority = priority;
        this.group = group;
        this.settingPublisher = settingPublisher;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int getState() {
        return prepared ? 0 : DefaultGroup.PREPARE;
    }

    @Override
    public void setState(int state) {
        switch (state) {
            case DefaultGroup.PREPARE:
                prepared = false;
                break;
            case 0:
                prepared = true;
        }
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean isSlave(Rank rank) {
        return getPriority() < rank.getPriority();
    }

    @Override
    public int compareTo(@NotNull Rank anotherRank) {
        return Ints.compare(getPriority(), anotherRank.getPriority());
    }

    protected boolean isPrepared() {
        return prepared;
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public String getColumn(int column) {
        return name;
    }


    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (isPrepared()) {
            settingPublisher.publish(group, target, setting, value);
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (isPrepared()) {
            settingPublisher.publish(group, target, setting, null);
        }
    }
}
