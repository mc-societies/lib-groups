package net.catharos.groups;

import org.jetbrains.annotations.Nullable;

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
    public boolean cache(Group group) {
        return false;
    }

    @Nullable
    @Override
    public Group clear(Group group) {
        return null;
    }
}
