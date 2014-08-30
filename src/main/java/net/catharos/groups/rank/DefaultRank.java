package net.catharos.groups.rank;

import com.google.common.primitives.Ints;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.catharos.groups.DefaultGroup;
import net.catharos.groups.publisher.SettingPublisher;
import net.catharos.groups.setting.subject.AbstractPublishingSubject;
import org.jetbrains.annotations.NotNull;

import javax.inject.Provider;
import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends AbstractPublishingSubject implements Rank {

    private final UUID uuid;
    private final String name;
    private final int priority;

    private boolean prepared = false;

    @AssistedInject
    public DefaultRank(Provider<UUID> uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       SettingPublisher settingPublisher) {
        this(uuid.get(), name, priority, settingPublisher);
    }

    @AssistedInject
    public DefaultRank(@Assisted UUID uuid,
                       @Assisted String name,
                       @Assisted int priority,
                       SettingPublisher settingPublisher) {
        super(settingPublisher);
        this.uuid = uuid;
        this.name = name;
        this.priority = priority;
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
    public boolean isSlave(Rank rank) {
        return getPriority() < rank.getPriority();
    }

    @Override
    public int compareTo(@NotNull Rank anotherRank) {
        return Ints.compare(getPriority(), anotherRank.getPriority());
    }

    @Override
    protected boolean isPrepared() {
        return prepared;
    }
}
