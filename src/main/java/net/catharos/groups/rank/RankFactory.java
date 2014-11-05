package net.catharos.groups.rank;

import com.google.inject.name.Named;
import net.catharos.groups.Group;

import java.util.UUID;

/**
 * Represents a RankFactory
 */
public interface RankFactory {

    Rank create(UUID uuid, String name, int priority, Group group);

    Rank create(String name, int priority, Group group);

    @Named("static")
    Rank createStatic(UUID uuid, String name, int priority);
}
