package net.catharos.groups.publisher;

import net.catharos.groups.Member;
import org.joda.time.DateTime;

/**
 * Represents a LastActivePublisher
 */
public interface LastActivePublisher {

    void publish(Member member, DateTime date);
}
