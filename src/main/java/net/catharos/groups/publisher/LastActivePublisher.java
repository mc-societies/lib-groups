package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import org.joda.time.DateTime;

/**
 * Represents a LastActivePublisher
 */
public interface LastActivePublisher {

    void publish(Group group, DateTime date);
}
