package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;
import org.joda.time.DateTime;

/**
 * Represents a CreatedPublisher
 */
public interface MemberCreatedPublisher {

    <M extends Member> ListenableFuture<M> publishCreated(M member, DateTime created);
}
