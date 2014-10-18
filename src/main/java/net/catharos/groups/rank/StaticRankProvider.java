package net.catharos.groups.rank;

import net.catharos.groups.Group;

import java.util.UUID;

/**
 * Represents a RankProvider
 */
public class StaticRankProvider {

    private final UUID uuid;
    private final String name;
    private final int priority;

    public StaticRankProvider(UUID uuid, String name, int priority) {
        this.uuid = uuid;
        this.name = name;
        this.priority = priority;
    }

    public Rank provide(Group group) {
        return new StaticRank(uuid, name, priority, group);
    }
}
