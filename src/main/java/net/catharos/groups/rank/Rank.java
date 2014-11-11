package net.catharos.groups.rank;

import net.catharos.groups.Group;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.lib.core.command.format.table.RowForwarder;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a Rank
 */
public interface Rank extends Comparable<Rank>, Subject, RowForwarder {

    int DEFAULT_PRIORITY = 0;

    @Override
    UUID getUUID();

    String getName();

    int getPriority();

    int getState();

    void setState(int state);

    /**
     * @return null if this belongs to no specific group
     */
    @Nullable
    Group getGroup();

    boolean isStatic();

    void addRule(String rule);

    boolean hasRule(String rule);

    boolean isSlave(Rank rank);

}
