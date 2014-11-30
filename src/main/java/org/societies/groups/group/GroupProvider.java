package org.societies.groups.group;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.Set;
import java.util.UUID;

/**
 * A {@link GroupProvider} represents a data source for {@link Group}s.
 * If a group does not exist in a external data source a new one will be created. {@link GroupFactory}
 */
public interface GroupProvider {

    /**
     * @param uuid The uuid to look up
     * @return A future of the group
     */
    ListenableFuture<Group> getGroup(UUID uuid);

    /**
     * @param tag The tag to look up
     * @return A future of the group
     */
    ListenableFuture<Set<Group>> getGroup(String tag);

    /**
     * @return All existing groups
     */
    ListenableFuture<Set<Group>> getGroups();

    ListenableFuture<Integer> size();
}
