package net.catharos.groups;

import com.google.common.base.Objects;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.MemberCreatedPublisher;
import net.catharos.groups.publisher.MemberGroupPublisher;
import net.catharos.groups.publisher.MemberLastActivePublisher;
import net.catharos.groups.publisher.MemberRankPublisher;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Request;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractSubject;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.UUID;

/**
 * Default implementation for a Member
 */
public abstract class DefaultMember extends AbstractSubject implements Member {

    private final UUID uuid;

    private boolean completed = false;
    @Nullable
    private Group group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private DateTime lastActive;
    private DateTime created;

    private final MemberGroupPublisher groupPublisher;
    private final MemberRankPublisher memberRankPublisher;
    private final MemberLastActivePublisher lastActivePublisher;
    private final MemberCreatedPublisher createdPublisher;

    @Nullable
    private Request receivedRequest, suppliedRequest;

    public DefaultMember(UUID uuid,
                         MemberGroupPublisher groupPublisher,
                         MemberRankPublisher memberRankPublisher,
                         MemberLastActivePublisher lastActivePublisher,
                         MemberCreatedPublisher createdPublisher) {
        this.uuid = uuid;
        this.groupPublisher = groupPublisher;
        this.memberRankPublisher = memberRankPublisher;
        this.lastActivePublisher = lastActivePublisher;
        this.createdPublisher = createdPublisher;

        this.created = this.lastActive = DateTime.now();
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

        if (result && isCompleted()) {
            memberRankPublisher.publishRank(this, rank);
        }
    }

    @Override
    public <V> V getRankValue(Setting<V> setting) {
        for (Rank rank : ranks) {
            V value = rank.getBoolean(setting);

            if (value != null) {
                return value;
            }
        }

        return null;
    }

    @Override
    public boolean hasRule(String rule) {
        for (Rank rank : ranks) {
            if (rank.hasRule(rule)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public DateTime getLastActive() {
        return lastActive;
    }

    @Override
    public void activate() {
        setLastActive(DateTime.now());
    }

    @Override
    public void setLastActive(DateTime lastActive) {
        this.lastActive = lastActive;

        if (isCompleted()) {
            lastActivePublisher.publishLastActive(this, lastActive);
        }
    }

    @Override
    public DateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(DateTime created) {
        this.created = created;

        if (isCompleted()) {
            createdPublisher.publishCreated(this, created);
        }
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

        if (isCompleted()) {
            groupPublisher.publishGroup(this, group);
        }

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
    public Request getReceivedRequest() {
        return receivedRequest;
    }

    @Override
    public void setReceivedRequest(@Nullable Request receivedRequest) {
        this.receivedRequest = receivedRequest;
    }

    @Override
    @Nullable
    public Request getSuppliedRequest() {
        return suppliedRequest;
    }

    @Override
    public void setSuppliedRequest(@Nullable Request suppliedRequest) {
        this.suppliedRequest = suppliedRequest;
    }

    @Override
    public void clearReceivedRequest() {
        setReceivedRequest(null);
    }

    @Override
    public void clearSuppliedRequest() {
        setSuppliedRequest(null);
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
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void complete() {
        completed = true;
    }


}
