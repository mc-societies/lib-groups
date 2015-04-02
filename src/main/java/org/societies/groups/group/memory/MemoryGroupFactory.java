package org.societies.groups.group.memory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.joda.time.DateTime;
import org.societies.groups.ExtensionFactory;
import org.societies.groups.ExtensionRoller;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupFactory;
import org.societies.groups.group.GroupPublisher;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a DefaultGroupFactory
 */
public class MemoryGroupFactory implements GroupFactory {

    private final Provider<UUID> uuidProvider;
    private final GroupPublisher groupPublisher;
    private final ExtensionFactory<MemoryGroupHeart, Group> heartFactory;
    private final Set<ExtensionRoller<Group>> extensions;

    @Inject
    public MemoryGroupFactory(
            Provider<UUID> uuidProvider,
            GroupPublisher groupPublisher,
            ExtensionFactory<MemoryGroupHeart, Group> heartFactory,
            Set<ExtensionRoller<Group>> extensions) {
        this.uuidProvider = uuidProvider;
        this.groupPublisher = groupPublisher;
        this.heartFactory = heartFactory;
        this.extensions = extensions;
    }

    @Override
    public Group create(String name, String tag) {
        return create(uuidProvider.get(), name, tag);
    }

    @Override
    public Group create(String name, String tag, DateTime created) {
        return create(uuidProvider.get(), name, tag, created);
    }

    @Override
    public Group create(UUID uuid, String name, String tag) {
        return create(uuid, name, tag, DateTime.now());
    }

    @Override
    public Group create(final UUID uuid, String name, String tag, DateTime created) {
        Group group = new Group(uuid);

        MemoryGroupHeart heart = heartFactory.create(group);

        group.setGroupHeart(heart);

        group.unlink();
        heart.setName(name);
        heart.setTag(tag);
        heart.setCreated(created);

        for (ExtensionRoller<Group> extension : extensions) {
            extension.roll(group);
        }

        group.link();
        return group;
    }
}
