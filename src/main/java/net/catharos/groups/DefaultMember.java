package net.catharos.groups;

import gnu.trove.set.hash.THashSet;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Represents a DefaultMember
 */
public class DefaultMember implements Member {

    private final UUID uuid;

    private final THashSet<Group> groups = new THashSet<Group>();

    public DefaultMember(UUID uuid) {this.uuid = uuid;}

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

    @Override
    public boolean hasGroup(Group group) {
        return groups.contains(group);
    }

    @Override
    public String toString() {
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", groups=" + groups +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultMember that = (DefaultMember) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
