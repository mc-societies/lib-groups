package org.societies.groups.member;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import gnu.trove.set.hash.THashSet;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.societies.groups.event.Event;
import org.societies.groups.event.EventController;
import org.societies.groups.event.MemberJoinEvent;
import org.societies.groups.event.MemberLeaveEvent;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.publisher.MemberCreatedPublisher;
import org.societies.groups.publisher.MemberGroupPublisher;
import org.societies.groups.publisher.MemberLastActivePublisher;
import org.societies.groups.publisher.MemberRankPublisher;
import org.societies.groups.rank.Rank;

import java.util.Collections;
import java.util.Set;

/**
 * Default implementation for a Member
 */
public class DefaultMemberHeart extends AbstractMemberHeart implements MemberHeart {

    private final Statics statics;
    private final Member member;

    @Nullable
    private GroupHeart group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private DateTime lastActive;
    private DateTime created;

    @Inject
    public DefaultMemberHeart(Statics statics, @Assisted Member member) {
        this.statics = statics;
        this.member = member;
        this.created = this.lastActive = DateTime.now();
    }

    @Override
    public Member getHolder() {
        return member;
    }

    @Override
    public Set<Rank> getRanks() {
        if (getGroup() == null) {
            return Collections.emptySet();
        }

        return Sets.union(ranks, Collections.singleton(statics.getDefaultRank()));
    }

    @Override
    public void addRank(Rank rank) {
        if (getGroup() == null) {
            return;
        }

        boolean result = this.ranks.add(rank);

        if (result && member.isCompleted()) {
            statics.publishRank(member, rank);
        }
    }

    @Override
    public boolean removeRank(Rank rank) {
        if (getGroup() == null) {
            return false;
        }

        boolean result = ranks.remove(rank);

        if (result && member.isCompleted()) {
            statics.dropRank(member, rank);
        }

        return result;
    }


    @Override
    public DateTime getLastActive() {
        return lastActive;
    }

    @Override
    public void setLastActive(DateTime lastActive) {
        this.lastActive = lastActive;

        if (member.isCompleted()) {
            statics.publishLastActive(member, lastActive);
        }
    }

    @Override
    public DateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(DateTime created) {
        this.created = created;

        if (member.isCompleted()) {
            statics.publishCreated(member, created);
        }
    }

    @Override
    @Nullable
    public GroupHeart getGroup() {
        return group;
    }

    @Override
    public void setGroup(@Nullable GroupHeart group) {
        if (Objects.equal(this.group, group)) {
            return;
        }

        GroupHeart previous = this.group;

        this.group = group;

        if (member.isCompleted()) {
            statics.publishGroup(member, group == null ? null : group.getHolder());
        }

        if (group == null) {
            //fixme clear ranks in database
            this.ranks.clear();
            statics.publish(new MemberLeaveEvent(member, previous == null ? null : previous.getHolder()));
        } else {
            statics.publish(new MemberJoinEvent(member));
        }

        if (group != null && !group.isMember(member)) {
            group.addMember(member);
        }
    }

    @Singleton
    public static class Statics {
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
