package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.rank.Rank;

/**
 * Represents a RankPublisher
 */
public interface RankDropPublisher {

    ListenableFuture<Rank> drop(Rank rank);
}
