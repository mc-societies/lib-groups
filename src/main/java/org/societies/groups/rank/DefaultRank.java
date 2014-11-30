package org.societies.groups.rank;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.Nullable;
import org.societies.groups.Completable;
import org.societies.groups.group.Group;
import org.societies.groups.publisher.SettingPublisher;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

import javax.inject.Provider;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends AbstractRank implements Completable {

    private final Group group;

    private final SettingPublisher settingPublisher;

    private boolean completed = true;

    @AssistedInject
    public DefaultRank(Provider<UUID> uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       @Assisted @Nullable Group group,
                       SettingPublisher settingPublisher,
                       Map<String, Setting<Boolean>> rules) {
        this(uuid.get(), name, priority, group, settingPublisher, rules);
    }

    @AssistedInject
    public DefaultRank(@Assisted UUID uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       @Assisted @Nullable Group group,
                       SettingPublisher settingPublisher,
                       Map<String, Setting<Boolean>> rules) {
        super(uuid, name, priority, rules);
        this.group = group;
        this.settingPublisher = settingPublisher;
    }


    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void complete(boolean value) {
        this.completed = value;
    }

    @Override
    public void complete() {
        complete(true);
    }

    @Nullable
    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (isCompleted()) {
            settingPublisher.publish(group, target, setting, value);
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (isCompleted()) {
            settingPublisher.publish(group, target, setting, null);
        }
    }
}
