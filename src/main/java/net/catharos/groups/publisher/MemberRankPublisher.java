package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;
import net.catharos.groups.rank.Rank;

/**
 * Represents a MemberRankPublisher
 */
public interface MemberRankPublisher {

    ListenableFuture<Member> publishRank(Member member, Rank rank);

    ListenableFuture<Member> dropRank(Member member, Rank rank);
}
