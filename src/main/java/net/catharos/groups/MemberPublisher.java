package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

/**
 *
 */
public interface MemberPublisher<M extends Member> {

    ListenableFuture<M> publish(M member);
}
