package net.catharos.groups.rank;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.catharos.groups.DefaultGroup;
import net.catharos.groups.Group;
import net.catharos.groups.publisher.SettingPublisher;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import org.jetbrains.annotations.Nullable;

import javax.inject.Provider;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends AbstractRank {

    private final Group group;

    private final SettingPublisher settingPublisher;

    private boolean prepared = false;

    @AssistedInject
    public DefaultRank(Provider<UUID> uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       @Assisted @Nullable Group group,
                       SettingPublisher settingPublisher,
                       Map<String,  Setting<Boolean>> rules) {
        this(uuid.get(), name, priority, group, settingPublisher, rules);
    }

    @AssistedInject
    public DefaultRank(@Assisted UUID uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       @Assisted @Nullable Group group,
                       SettingPublisher settingPublisher,
                       Map<String,  Setting<Boolean>> rules) {
        super(uuid, name, priority, rules);
        this.group = group;
        this.settingPublisher = settingPublisher;
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

    @Nullable
    @Override
    public Group getGroup() {
        return group;
    }

    protected boolean isPrepared() {
        return prepared;
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
