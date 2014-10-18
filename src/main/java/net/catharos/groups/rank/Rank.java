package net.catharos.groups.rank;

import net.catharos.groups.Group;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.lib.core.command.format.table.RowForwarder;

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

    Group getGroup();

    boolean isSlave(Rank rank);

}
