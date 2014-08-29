package net.catharos.groups.publisher;

import net.catharos.groups.Group;

/**
 * Represents a MemberStatePublisher
 */
public interface GroupStatePublisher {

    void publish(Group group, short state);
}
