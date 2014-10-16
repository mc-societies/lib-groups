package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.Set;
import java.util.UUID;

import static com.google.common.util.concurrent.Futures.immediateFuture;

/**
 * Represents a GroupCacheAdapter
 */
public class GroupCacheAdapter implements GroupProvider {

    private final GroupCache groupCache;

    public GroupCacheAdapter(GroupCache groupCache) {this.groupCache = groupCache;}

    @Override
    public ListenableFuture<Group> getGroup(UUID uuid) {
        return immediateFuture(groupCache.getGroup(uuid));
    }

    @Override
    public ListenableFuture<Set<Group>> getGroup(String tag) {
        return immediateFuture(groupCache.getGroup(tag));
    }

    @Override
    public ListenableFuture<Set<Group>> getGroups() {
        return immediateFuture(groupCache.getGroups());
    }
}
