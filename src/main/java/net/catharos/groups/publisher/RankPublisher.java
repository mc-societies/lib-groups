package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import net.catharos.groups.rank.Rank;

/**
 * Represents a RankPublisher
 */
public interface RankPublisher {

    void publishRank(Group group, Rank rank);
}
