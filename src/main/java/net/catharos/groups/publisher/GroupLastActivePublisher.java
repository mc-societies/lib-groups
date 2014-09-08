package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import org.joda.time.DateTime;

/**
 * Represents a LastActivePublisher
 */
public interface GroupLastActivePublisher {

    void publish(Group group, DateTime date);
}
