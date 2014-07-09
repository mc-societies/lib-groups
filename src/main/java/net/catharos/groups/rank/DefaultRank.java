package net.catharos.groups.rank;

import com.google.common.primitives.Ints;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.catharos.groups.setting.subject.DefaultSubject;
import org.jetbrains.annotations.NotNull;

import javax.inject.Provider;
import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends DefaultSubject implements Rank {

    private final UUID uuid;
    private final String name;
    private final int priority;

    @AssistedInject
    public DefaultRank(Provider<UUID> uuid, @Assisted String name, @Assisted int priority) {
        this(uuid.get(), name, priority);
    }

    @AssistedInject
    public DefaultRank(@Assisted UUID uuid, @Assisted String name, @Assisted int priority) {
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
    public boolean isSlave(Rank rank) {
        return getPriority() < rank.getPriority();
    }

    @Override
    public int compareTo(@NotNull Rank anotherRank) {
        return Ints.compare(getPriority(), anotherRank.getPriority());
    }
}
