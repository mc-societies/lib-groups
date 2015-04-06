package org.societies.groups.rank;

import order.format.table.RowForwarder;
import org.societies.groups.Linkable;

import java.util.Set;

/**
 * Represents a Rank
 */
public interface Rank extends Comparable<Rank>, RowForwarder, Linkable {

    int DEFAULT_PRIORITY = 0;

    String getName();

    int getPriority();

    boolean isStatic();

    void addRule(String rule);

    void removeRule(String rule);

    boolean hasRule(String rule);

    Set<String> getRules();

    boolean isSlave(Rank rank);

}
