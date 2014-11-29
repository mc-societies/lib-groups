package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;

/**
 * Represents a MemberDropPublisher
 */
public interface MemberDropPublisher {

    ListenableFuture<Member> drop(Member member);
}
