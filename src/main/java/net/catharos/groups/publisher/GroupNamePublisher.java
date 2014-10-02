package net.catharos.groups.publisher;

import net.catharos.groups.Group;

/**
 * Represents a NamePublisher
 */
public interface GroupNamePublisher {

    void publish(Group group, String name);
}
