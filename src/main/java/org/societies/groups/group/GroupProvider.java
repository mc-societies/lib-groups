package org.societies.groups.group;

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
    Group getGroup(UUID uuid);

    /**
     * @param tag The tag to look up
     * @return A future of the group
     */
    Set<Group> getGroup(String tag);

    /**
     * @return All existing groups
     */
    Set<Group> getGroups();

    Integer size();
}
