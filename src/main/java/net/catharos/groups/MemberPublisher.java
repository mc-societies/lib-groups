package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Used to publish new members to a data source and drop existing ones
 */
@Deprecated
public interface MemberPublisher<M extends Member> {

    /**
     * Publishes a new member to a data source
     *
     * @param member The member
     * @return A future which will fire when the member was published
     */
    ListenableFuture<M> publish(M member);

    /**
     * Drops a member from a data source
     *
     * @param member The member
     * @return A future which will fire when the member was dropped
     */
    ListenableFuture<?> drop(M member);
}
