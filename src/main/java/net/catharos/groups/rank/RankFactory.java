package net.catharos.groups.rank;

import java.util.UUID;

/**
 * Represents a RankFactory
 */
public interface RankFactory {

    Rank create(UUID uuid, String name, int priority);

    Rank create(String name, int priority);
}
