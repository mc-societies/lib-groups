package org.societies.groups.rank;

import com.google.inject.name.Named;
import org.societies.groups.group.Group;

/**
 * Represents a RankFactory
 */
public interface RankFactory {

    Rank create(String name, int priority, Group owner);

    @Named("static")
    Rank createStatic(String name, int priority, Iterable<String> rules);
}
