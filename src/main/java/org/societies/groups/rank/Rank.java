package org.societies.groups.rank;

import net.catharos.lib.core.command.format.table.RowForwarder;
import org.societies.groups.Linkable;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a Rank
 */
public interface Rank extends Comparable<Rank>, RowForwarder, Linkable {

    int DEFAULT_PRIORITY = 0;

    UUID getUUID();

    String getName();

    int getPriority();

    boolean isStatic();

    void addRule(String rule);

    void removeRule(String rule);

    boolean hasRule(String rule);

    Set<String> getAvailableRules();

    boolean isSlave(Rank rank);

}
