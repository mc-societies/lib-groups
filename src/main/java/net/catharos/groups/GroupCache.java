package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a GroupProvider
 */
public interface GroupCache {

    Group getGroup(UUID uuid);
}
