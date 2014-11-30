package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.Group;
import org.societies.groups.rank.Rank;

/**
 * Represents a GroupRankPublisher
 */
public interface GroupRankPublisher {

    ListenableFuture<Group> publishRank(Group group, Rank rank);
}
