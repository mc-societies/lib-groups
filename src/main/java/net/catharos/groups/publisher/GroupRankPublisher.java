package net.catharos.groups.publisher;

import net.catharos.groups.Group;
import net.catharos.groups.rank.Rank;

/**
 * Represents a GroupRankPublisher
 */
public interface GroupRankPublisher {

    void publish(Group group, Rank rank);
}
