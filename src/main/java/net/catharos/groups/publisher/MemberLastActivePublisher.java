package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;
import org.joda.time.DateTime;

/**
 * Represents a LastActivePublisher
 */
public interface MemberLastActivePublisher {

    <M extends Member> ListenableFuture<M> publishLastActive(M member, DateTime date);
}
