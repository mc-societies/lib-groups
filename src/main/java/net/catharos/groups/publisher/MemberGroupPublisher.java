package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Group;
import net.catharos.groups.Member;

/**
 * Represents a GroupPublisher
 */
public interface MemberGroupPublisher<M extends Member> {

    ListenableFuture<M> publishGroup(M member, Group group);
}
