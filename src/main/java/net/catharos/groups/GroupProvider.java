package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a GroupProvider
 */
public interface GroupProvider {

    Group getGroup(UUID uuid);
}
