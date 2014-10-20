package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Member;

/**
 * Used to publish new members to a data source and drop existing ones
 */
public interface MemberPublisher {

    /**
     * Publishes a new member to a data source
     *
     * @param member The member
     * @return A future which will fire when the member was published
     */
    <M extends Member> ListenableFuture<M> publish(M member);
}
