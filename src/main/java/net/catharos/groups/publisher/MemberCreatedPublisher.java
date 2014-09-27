package net.catharos.groups.publisher;

import net.catharos.groups.Member;
import org.joda.time.DateTime;

/**
 * Represents a CreatedPublisher
 */
public interface MemberCreatedPublisher {

    void publish(Member member, DateTime created);
}
