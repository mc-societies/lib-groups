package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;
import org.societies.groups.member.Member;

/**
 * Represents a LastActivePublisher
 */
public interface MemberLastActivePublisher {

    ListenableFuture<Member> publishLastActive(Member member, DateTime date);
}
