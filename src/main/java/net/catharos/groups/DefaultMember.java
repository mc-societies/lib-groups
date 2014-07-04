package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Request;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Default implementation for a Member
 */
public class DefaultMember implements Member {

    private final UUID uuid;

    @Nullable
    private Group group;
    private Rank rank;
    private Request activeRequest;

    public DefaultMember(UUID uuid) {this.uuid = uuid;}

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Rank getRank() {
        return rank;
    }

    @Override
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    @Nullable
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(@Nullable Group group) {
        this.group = group;

        if (group != null && !group.isMember(this)) {
            group.addMember(this);
        }
    }

    @Override
    public boolean isGroup(Group group) {
        return this.group != null && this.group.equals(group);
    }

    @Override
    public String toString() {
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", group=" + group +
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

    @Nullable
    @Override
    public Request getActiveRequest() {
        return activeRequest;
    }

    @Override
    public void setActiveRequest(Request activeRequest) {
        this.activeRequest = activeRequest;
    }

    @Override
    public boolean clearRequest() {
        boolean value = activeRequest != null;
        activeRequest = null;
        return value;
    }


}
