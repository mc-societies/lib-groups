package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.event.EventController;
import net.catharos.groups.event.MemberJoinEvent;
import net.catharos.groups.event.MemberLeaveEvent;
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

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * Default implementation for a Member
 */
public abstract class DefaultMember extends AbstractSubject implements Member {

    private final UUID uuid;

    private boolean completed = true;
    @Nullable
    private Group group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private DateTime lastActive;
    private DateTime created;

    private final Rank defaultRank;

    @Nullable
    private Request receivedRequest, suppliedRequest;

    private final MemberGroupPublisher groupPublisher;
    private final MemberRankPublisher memberRankPublisher;
    private final MemberLastActivePublisher lastActivePublisher;
    private final MemberCreatedPublisher createdPublisher;

    private final EventController eventController;

    public DefaultMember(UUID uuid,
                         MemberGroupPublisher groupPublisher,
                         MemberRankPublisher memberRankPublisher,
                         MemberLastActivePublisher lastActivePublisher,
                         MemberCreatedPublisher createdPublisher,
                         Rank defaultRank, EventController eventController) {
        this.uuid = uuid;
        this.groupPublisher = groupPublisher;
        this.memberRankPublisher = memberRankPublisher;
        this.lastActivePublisher = lastActivePublisher;
        this.createdPublisher = createdPublisher;
        this.defaultRank = defaultRank;
        this.eventController = eventController;

        this.created = this.lastActive = DateTime.now();
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Set<Rank> getRanks() {
        if (getGroup() == null) {
            return Collections.emptySet();
        }

        return Sets.union(ranks, Collections.singleton(defaultRank));
    }

    @Override
    public void addRank(Rank rank) {
        if (getGroup() == null) {
            return;
        }

        boolean result = this.ranks.add(rank);

        if (result && isCompleted()) {
            memberRankPublisher.publishRank(this, rank);
        }
    }

    @Override
    public boolean hasRank(Rank rank) {
        return getRanks().contains(rank);
    }

    @Override
    public boolean removeRank(Rank rank) {
        if (getGroup() == null) {
            return false;
        }

        boolean result = ranks.remove(rank);

        if (result && isCompleted()) {
            memberRankPublisher.dropRank(this, rank);
        }

        return result;
    }

    @Override
    public Rank getRank() {
        if (getGroup() == null) {
            return null;
        }

        Rank highest = null;

        for (Rank rank : getRanks()) {
            if (highest == null || rank.getPriority() > highest.getPriority()) {
                highest = rank;
            }
        }

        return highest;
    }

    @Override
    public <V> V getRankValue(Setting<V> setting) {
        for (Rank rank : getRanks()) {
            V value = rank.get(setting);

            if (value != null) {
                return value;
            }
        }

        return null;
    }


    @Override
    public boolean hasRule(String rule) {
        for (Rank rank : getRanks()) {
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
    public boolean hasGroup() {
        return getGroup() != null;
    }

    @Override
    public void setGroup(@Nullable Group group) {
        if (Objects.equal(this.group, group)) {
            return;
        }

        Group previous = this.group;

        this.group = group;

        if (isCompleted()) {
            groupPublisher.publishGroup(this, group);
        }

        if (group == null) {
            this.ranks.clear();
            eventController.publish(new MemberLeaveEvent(this, previous));
        } else {
            eventController.publish(new MemberJoinEvent(this));
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
    public void complete(boolean value) {
        this.completed = value;
    }

    @Override
    public void complete() {
        complete(true);
    }

    @Override
    public boolean getBooleanRankValue(Setting<Boolean> setting) {
        Boolean value = getRankValue(setting);

        if (value == null) {
            return false;
        }

        return value;
    }
}
