package net.catharos.groups;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a MemberCache
 */
public interface GroupCache {

    Group getGroup(UUID uuid);

    Set<Group> getGroup(String name);

    Set<Group> getGroups();

    void cache(Group group);

    void clear(Group group);
}
