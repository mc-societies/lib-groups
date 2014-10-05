package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import org.joda.time.DateTime;

/**
 * Represents a CreatedPublisher
 */
public interface GroupCreatedPublisher {

    void publish(Group group, DateTime created);
}