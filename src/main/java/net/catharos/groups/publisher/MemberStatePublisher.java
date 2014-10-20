package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;

/**
 * Represents a MemberStatePublisher
 */
public interface MemberStatePublisher {

    <M extends Member> ListenableFuture<M> publishState(M member, short state);
}
