package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;
import org.joda.time.DateTime;

/**
 * Represents a CreatedPublisher
 */
public interface MemberCreatedPublisher {

    ListenableFuture<Member> publishCreated(Member member, DateTime created);
}
