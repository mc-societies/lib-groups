package net.catharos.groups.publisher;

import net.catharos.groups.Member;
import net.catharos.groups.rank.Rank;

/**
 * Represents a MemberRankPublisher
 */
public interface MemberRankPublisher {

    void publish(Member member, Rank rank);
}
