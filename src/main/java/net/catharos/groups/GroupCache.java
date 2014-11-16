package net.catharos.groups;

import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a MemberCache
 */
public interface GroupCache {

    @Nullable
    Group getGroup(UUID uuid);

    @Nullable
    Set<Group> getGroup(String name);

    @Nullable
    Set<Group> getGroups();

    int size();

    boolean cache(Group group);

    @Nullable
    Group clear(Group group);
}
