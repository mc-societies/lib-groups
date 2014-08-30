package net.catharos.groups;

import com.google.common.base.Objects;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.MemberGroupPublisher;
import net.catharos.groups.publisher.MemberRankPublisher;
import net.catharos.groups.publisher.MemberStatePublisher;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Request;
import net.catharos.groups.setting.Setting;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Default implementation for a Member
 */
public abstract class DefaultMember implements Member {

    public static final int PREPARE = 0xFBEFABE;

    private final UUID uuid;

    private short state;
    @Nullable
    private Group group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private Request activeRequest;

    private final MemberGroupPublisher groupPublisher;
    private final MemberStatePublisher memberStatePublisher;
    private final MemberRankPublisher memberRankPublisher;

    public DefaultMember(UUID uuid,
                         MemberGroupPublisher groupPublisher,
                         MemberStatePublisher memberStatePublisher,
                         MemberRankPublisher memberRankPublisher) {
        this.uuid = uuid;
        this.groupPublisher = groupPublisher;
        this.memberStatePublisher = memberStatePublisher;
        this.memberRankPublisher = memberRankPublisher;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Set<Rank> getRanks() {
        return ranks;
    }

    @Override
    public void addRank(Rank rank) {
        boolean result = this.ranks.add(rank);

        if (result && isPrepared()) {
            memberRankPublisher.publish(this, rank);
        }
    }

    @Override
    public <V> V getSingle(Setting<V> setting) {
        for (Rank rank : ranks) {
            V value = rank.get(setting);

            if (value != null) {
                return value;
            }
        }

        return null;
    }

    @Override
    @Nullable
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(@Nullable Group group) {
        if (Objects.equal(this.group, group)) {
            return;
        }

        this.group = group;

        if (isPrepared()) {
            groupPublisher.publish(this, group);
        }

        if (group != null && !group.isMember(this)) {
            group.addMember(this);
        }
    }

    private boolean isPrepared() {
        return getState() != PREPARE;
    }

    @Override
    public boolean isGroup(Group group) {
        return this.group != null && this.group.equals(group);
    }

    @Override
    public String toString() {
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", group=" + (group != null ? group.getName() : null) +
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

    @Override
    public String getColumn(int column) {
        return getName();
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        short newState = (short) state;

        if (this.state != state && isPrepared()) {
            memberStatePublisher.publish(this, newState);
        }

        this.state = newState;
    }
}
