package net.catharos.groups;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a GroupProvider
 */
public interface GroupProvider {

    Group getGroup(UUID uuid);

    Set<Group> getGroup(String name);

    Set<Group> getGroups();
}
