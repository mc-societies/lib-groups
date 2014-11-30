package org.societies.groups.rank;

import net.catharos.lib.core.command.format.table.RowForwarder;
import org.jetbrains.annotations.Nullable;
import org.societies.groups.Completable;
import org.societies.groups.group.Group;
import org.societies.groups.setting.subject.Subject;

import java.util.UUID;

/**
 * Represents a Rank
 */
public interface Rank extends Comparable<Rank>, Subject, RowForwarder, Completable {

    int DEFAULT_PRIORITY = 0;

    @Override
    UUID getUUID();

    String getName();

    int getPriority();

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
