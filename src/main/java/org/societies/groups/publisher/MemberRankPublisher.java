package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;

/**
 * Represents a MemberRankPublisher
 */
public interface MemberRankPublisher {

    ListenableFuture<Member> publishRank(Member member, Rank rank);

    ListenableFuture<Member> dropRank(Member member, Rank rank);
}
