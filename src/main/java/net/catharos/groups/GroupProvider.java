package net.catharos.groups;

import net.catharos.lib.core.concurrent.Future;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a GroupProvider
 */
public interface GroupProvider {

    Future<Group> getGroup(UUID uuid);

    Future<Set<Group>> getGroup(String name);

    Future<Set<Group>> getGroups();
}
