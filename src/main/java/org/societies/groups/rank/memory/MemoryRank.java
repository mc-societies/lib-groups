package org.societies.groups.rank.memory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.Nullable;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupPublisher;
import org.societies.groups.rank.DefaultRank;

import java.util.Set;

/**
 * Represents a MemoryRank
 */
public class MemoryRank extends DefaultRank {

    @AssistedInject
    public MemoryRank(@Assisted String name,
                      @Assisted int priority,
                      @Assisted @Nullable Group group,
                      Set<String> availableRules,
                      GroupPublisher groupPublisher) {
        super(name, priority, group, availableRules, groupPublisher);
    }
}
