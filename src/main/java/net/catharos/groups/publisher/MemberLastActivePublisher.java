package net.catharos.groups.publisher;

import net.catharos.groups.Member;
import org.joda.time.DateTime;

/**
 * Represents a LastActivePublisher
 */
public interface MemberLastActivePublisher {

    void publish(Member member, DateTime date);
}
