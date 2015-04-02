package org.societies.groups.rank;

import com.google.inject.name.Named;
import org.societies.groups.group.Group;

import java.util.UUID;

/**
 * Represents a RankFactory
 */
public interface RankFactory {

    Rank create(UUID uuid, String name, int priority, Group owner);

    Rank create(String name, int priority, Group owner);

    @Named("static")
    Rank createStatic(UUID uuid, String name, int priority, Iterable<String> rules);
}
