package org.societies.groups.cache;

import org.jetbrains.annotations.Nullable;
import org.societies.groups.group.Group;
import org.societies.groups.member.Member;

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

    @Nullable
    Group clear(Member leaving, Group group);
}
