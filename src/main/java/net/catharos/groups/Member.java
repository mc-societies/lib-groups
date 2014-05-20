package net.catharos.groups;

import java.util.Collection;
import java.util.UUID;

/**
 *
 */
public interface Member {

    UUID getUUID();

    Collection<Group> getGroups();

    void addGroup(Group group);

    boolean hasGroup(Group group);
}
