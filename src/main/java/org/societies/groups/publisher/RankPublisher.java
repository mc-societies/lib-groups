package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.rank.Rank;

/**
 * Represents a RankPublisher
 */
public interface RankPublisher {

    ListenableFuture<Rank> publish(Rank rank);
}
