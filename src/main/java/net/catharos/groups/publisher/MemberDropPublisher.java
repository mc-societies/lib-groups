package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;

/**
 * Represents a MemberDropPublisher
 */
public interface MemberDropPublisher<M extends Member> {

    ListenableFuture<M> drop(M member);
}
