package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import net.catharos.groups.rank.Rank;

/**
 * Represents a RankPublisher
 */
public interface RankDropPublisher {

    void drop(Group group, Rank rank);
}
