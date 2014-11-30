package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.member.Member;

/**
 * Represents a MemberDropPublisher
 */
public interface MemberDropPublisher {

    ListenableFuture<Member> drop(Member member);
}
