package org.societies.groups.cache;

import org.jetbrains.annotations.Nullable;
import org.societies.groups.group.Group;
import org.societies.groups.member.Member;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a NaughtyGroupCache
 */
public class NaughtyGroupCache implements GroupCache {

    @Nullable
    @Override
    public Group getGroup(UUID uuid) {
        return null;
    }

    @Nullable
    @Override
    public Set<Group> getGroup(String name) {
        return null;
    }

    @Nullable
    @Override
    public Set<Group> getGroups() {
        return null;
    }

    @Override
    public int size() {
        return -1;
    }

    @Override
    public boolean cache(Group group) {
        return false;
    }

    @Nullable
    @Override
    public Group clear(Group group) {
        return null;
    }

    @Nullable
    @Override
    public Group clear(Member leaving, Group group) {
        return null;
    }
}
