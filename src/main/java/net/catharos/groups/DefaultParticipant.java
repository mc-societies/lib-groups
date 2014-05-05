package net.catharos.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Represents a DefaultParticipant
 */
public class DefaultParticipant implements Member {

    private final UUID uuid;

    private final ArrayList<Group> groups = new ArrayList<Group>();

    public DefaultParticipant(UUID uuid) {this.uuid = uuid;}

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Collection<Group> getGroups() {
        return Collections.unmodifiableCollection(groups);
    }

    @Override
    public void addGroup(Group group) {
        this.groups.add(group);

        if (!group.isMember(this)) {
            group.addMember(this);
        }
    }
}
