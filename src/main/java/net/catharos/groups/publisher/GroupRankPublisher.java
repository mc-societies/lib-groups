package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Group;
import net.catharos.groups.rank.Rank;

/**
 * Represents a GroupRankPublisher
 */
public interface GroupRankPublisher {

    ListenableFuture<Group> publishRank(Group group, Rank rank);
}
