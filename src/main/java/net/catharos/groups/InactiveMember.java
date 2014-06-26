package net.catharos.groups;

import net.catharos.groups.request.Request;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents a InactiveMember
 */
public class InactiveMember implements Member {

    private final UUID uuid;

    public InactiveMember(UUID uuid) {this.uuid = uuid;}

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Collection<Group> getGroups() {
        throw new InactiveException(this);
    }

    @Override
    public Group getGroup() {
        throw new InactiveException(this);
    }

    @Override
    public void addGroup(Group group) {
        throw new InactiveException(this);
    }

    @Override
    public boolean hasGroup(Group group) {
        throw new InactiveException(this);
    }

    @Override
    public String toString() {
        return "InactiveMember{" +
                "uuid=" + uuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InactiveMember that = (InactiveMember) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Nullable
    @Override
    public Request getActiveRequest() {
        return null;
    }

    @Override
    public void setActiveRequest(Request activeRequest) {
        throw new InactiveException(this);
    }

    @Override
    public boolean clearRequest() {
        return false;
    }
}
