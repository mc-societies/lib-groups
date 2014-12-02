package org.societies.groups.rank;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Represents a RankPublisher
 */
public interface RankPublisher {

    ListenableFuture<Rank> publish(Rank rank);
}
