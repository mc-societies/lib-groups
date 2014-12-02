package org.societies.groups.member;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Represents a MemberDropPublisher
 */
public interface MemberDestructor {

    ListenableFuture<Member> destruct(Member member);
}
