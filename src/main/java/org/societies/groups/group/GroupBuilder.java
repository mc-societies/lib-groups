package org.societies.groups.group;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Represents a GroupBuilder
 */
public class GroupBuilder {

    private final GroupFactory groupFactory;
    private final Provider<UUID> uuidProvider;

    private UUID uuid;
    private String name, tag;

    private DateTime created;

    @Inject
    public GroupBuilder(GroupFactory groupFactory, Provider<UUID> uuidProvider) {
        this.groupFactory = groupFactory;
        this.uuidProvider = uuidProvider;
    }

    public Group build() {
        if (uuid == null) {
            uuid = uuidProvider.get();
        }

        Group group = groupFactory.create(uuid, name, tag, created);

        group.unlink();

        group.setCreated(created);

        group.link();
        return group;
    }

    public GroupBuilder setUUID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public GroupBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GroupBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
