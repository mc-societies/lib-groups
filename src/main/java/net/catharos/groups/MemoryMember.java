package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.catharos.groups.event.Event;
import net.catharos.groups.event.EventController;
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

import java.util.UUID;

/**
 * Represents a MemoryMember
 */
public abstract class MemoryMember extends AbstractSubject implements Member {

    private final UUID uuid;


    private boolean completed = true;


    @Nullable
    private Request receivedRequest;
    @Nullable
    private Request suppliedRequest;

    public MemoryMember(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String toString() {
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", group=" + (getGroup() != null ? getGroup().getName() : null) +
                '}';
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
    public boolean hasRank(Rank rank) {
        return getRanks().contains(rank);
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
    public void activate() {
        setLastActive(DateTime.now());
    }

    @Override
    public boolean getBooleanRankValue(Setting<Boolean> setting) {
        Boolean value = getRankValue(setting);

        if (value == null) {
            return false;
        }

        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemoryMember that = (MemoryMember) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public static final class Statics {
        private final MemberGroupPublisher groupPublisher;
        private final MemberRankPublisher memberRankPublisher;
        private final MemberLastActivePublisher lastActivePublisher;
        private final MemberCreatedPublisher createdPublisher;

        private final EventController eventController;
        private final Rank defaultRank;

        @Inject
        public Statics(MemberGroupPublisher groupPublisher,
                       MemberRankPublisher memberRankPublisher,
                       MemberLastActivePublisher lastActivePublisher,
                       MemberCreatedPublisher createdPublisher,
                       EventController eventController,
                       @Named("default-rank") Rank defaultRank) {
            this.groupPublisher = groupPublisher;
            this.memberRankPublisher = memberRankPublisher;
            this.lastActivePublisher = lastActivePublisher;
            this.createdPublisher = createdPublisher;
            this.eventController = eventController;
            this.defaultRank = defaultRank;
        }

        public ListenableFuture publishGroup(Member member, Group group) {
            return groupPublisher.publishGroup(member, group);
        }

        public ListenableFuture publishCreated(Member member, DateTime created) {
            return createdPublisher.publishCreated(member, created);
        }

        public ListenableFuture publishRank(Member member, Rank rank) {
            return memberRankPublisher.publishRank(member, rank);
        }

        public ListenableFuture dropRank(Member member, Rank rank) {
            return memberRankPublisher.dropRank(member, rank);
        }

        public ListenableFuture publishLastActive(Member member, DateTime date) {
            return lastActivePublisher.publishLastActive(member, date);
        }

        public Rank getDefaultRank() {
            return defaultRank;
        }

        public void publish(Event event) {
            eventController.publish(event);
        }
    }
}
