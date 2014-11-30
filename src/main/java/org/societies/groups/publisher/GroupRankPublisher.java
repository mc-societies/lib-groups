package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.rank.Rank;

/**
 * Represents a GroupRankPublisher
 */
public interface GroupRankPublisher {

    ListenableFuture<GroupHeart> publishRank(GroupHeart group, Rank rank);
}
