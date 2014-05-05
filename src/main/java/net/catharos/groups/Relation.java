package net.catharos.groups;

import org.jetbrains.annotations.Nullable;

/**
 *
 */
public interface Relation {

    @Nullable
    Group getTarget();

    Group getSource();

    @Nullable
    Group getOpposite(Group group);

    boolean contains(Group group);

}
