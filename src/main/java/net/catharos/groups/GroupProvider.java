package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.Set;
import java.util.UUID;

/**
 * A {@link net.catharos.groups.GroupProvider} represents a data source for {@link net.catharos.groups.Group}s.
 * If a group does not exist in a external data source a new one will be created. {@link net.catharos.groups.GroupFactory}
 */
public interface GroupProvider {

    /**
     * @param uuid The uuid to look up
     * @return A future of the group
     */
    ListenableFuture<Group> getGroup(UUID uuid);

    ListenableFuture<Group> getGroup(UUID uuid, Member predefined, ListeningExecutorService service);

    /**
     * @param name The name to look up
     * @return A future of the group
     */
    ListenableFuture<Set<Group>> getGroup(String name);

    /**
     * @return All existing groups
     */
    ListenableFuture<Set<Group>> getGroups();
}
