package net.catharos.groups;

import net.catharos.lib.core.command.format.table.RowForwarder;
import org.jetbrains.annotations.Nullable;

/**
 * A {@link net.catharos.groups.Relation} describes a relation between two groups. A source and a target.
 * Usually the source is the group which initiated this relation.
 */
public interface Relation extends RowForwarder {

    /**
     * @return The source
     */
    Group getSource();

    /**
     * @return The target
     */
    Group getTarget();

    /**
     * @return The opposite group in this relation. If the source is specified the target will be returned. If the target
     * is specified the source will be returned.
     */
    @Nullable
    Group getOpposite(Group group);

    /**
     * @param group The group
     * @return Whether this group participates in this relation
     */
    boolean contains(Group group);

}
