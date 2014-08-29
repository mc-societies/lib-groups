package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import net.catharos.groups.Member;

/**
 * Represents a GroupPublisher
 */
public interface MemberGroupPublisher {

    void publish(Member member, Group group);
}
