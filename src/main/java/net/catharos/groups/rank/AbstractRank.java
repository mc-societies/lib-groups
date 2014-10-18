package net.catharos.groups.rank;

import com.google.common.primitives.Ints;
import com.google.inject.assistedinject.Assisted;
import net.catharos.groups.Group;
import net.catharos.groups.setting.subject.AbstractSubject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a AbstractRank
 */
public abstract class AbstractRank extends AbstractSubject implements Rank {
    protected final UUID uuid;
    protected final String name;
    protected final int priority;
    protected Group group;

    public AbstractRank(@Assisted UUID uuid, @Assisted String name, @Assisted int priority, @Assisted @Nullable Group group) {
        this.uuid = uuid;
        this.group = group;
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
    public Group getGroup() {
        return group;
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
    public int getColumns() {
        return 1;
    }

    @Override
    public String getColumn(int column) {
        return name;
    }
}
