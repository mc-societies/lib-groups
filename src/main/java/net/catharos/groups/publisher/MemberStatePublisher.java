package net.catharos.groups.publisher;

import net.catharos.groups.Member;

/**
 * Represents a MemberStatePublisher
 */
public interface MemberStatePublisher {

    void publish(Member member, short state);
}
