package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a GroupFactory
 */
public interface GroupFactory {

    Group create(UUID uuid, String name);
}
