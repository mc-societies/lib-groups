package org.societies.groups.rank.memory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.Nullable;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupPublisher;
import org.societies.groups.rank.DefaultRank;

import javax.inject.Provider;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a MemoryRank
 */
public class MemoryRank extends DefaultRank {

    @AssistedInject
    public MemoryRank(Provider<UUID> uuid,
                      @Assisted String name,
                      @Assisted int priority,
                      @Assisted @Nullable Group group,
                      Set<String> availableRules,
                      GroupPublisher groupPublisher) {
        this(uuid.get(), name, priority, group, availableRules, groupPublisher);
    }

    @AssistedInject
    public MemoryRank(@Assisted UUID uuid,
                      @Assisted String name,
                      @Assisted int priority,
                      @Assisted @Nullable Group owner,
                      Set<String> availableRules,
                      GroupPublisher groupPublisher) {
        super(uuid, name, priority, owner, availableRules, groupPublisher);
    }
}
