package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;
import org.societies.groups.member.Member;

/**
 * Represents a CreatedPublisher
 */
public interface MemberCreatedPublisher {

    ListenableFuture<Member> publishCreated(Member member, DateTime created);
}
