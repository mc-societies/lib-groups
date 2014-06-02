package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a GroupProvider
 */
public interface GroupProvider {

    ListenableFuture<Group> getGroup(UUID uuid);

    ListenableFuture<Set<Group>> getGroup(String name);

    ListenableFuture<Set<Group>> getGroups();
}
