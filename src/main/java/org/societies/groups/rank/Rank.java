package org.societies.groups.rank;

import net.catharos.lib.core.command.format.table.RowForwarder;
import org.societies.groups.Linkable;
import org.societies.groups.setting.subject.Subject;

import java.util.UUID;

/**
 * Represents a Rank
 */
public interface Rank extends Comparable<Rank>, Subject, RowForwarder, Linkable {

    int DEFAULT_PRIORITY = 0;

    @Override
    UUID getUUID();

    String getName();

    int getPriority();

    boolean isStatic();

    void addRule(String rule);

    boolean hasRule(String rule);

    boolean isSlave(Rank rank);

}
