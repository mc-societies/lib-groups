package net.catharos.groups.rank;

import com.google.common.primitives.Ints;
import net.catharos.groups.setting.subject.DefaultSubject;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends DefaultSubject implements Rank {

    public static final int DEFAULT_PRIORITY = 0;

    private final UUID uuid;
    private final String name;
    private final int priority;

    public DefaultRank(UUID uuid, String name) {this(uuid, name, DEFAULT_PRIORITY);}

    public DefaultRank(UUID uuid, String name, int priority) {
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
