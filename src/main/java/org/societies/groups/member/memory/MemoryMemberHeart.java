package org.societies.groups.member.memory;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import gnu.trove.set.hash.THashSet;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.societies.groups.Linkable;
import org.societies.groups.event.EventController;
import org.societies.groups.event.MemberJoinEvent;
import org.societies.groups.event.MemberLeaveEvent;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.member.AbstractMemberHeart;
import org.societies.groups.member.Member;
import org.societies.groups.member.MemberHeart;
import org.societies.groups.member.MemberPublisher;
import org.societies.groups.rank.Rank;

import java.util.Collections;
import java.util.Set;

/**
 * Default implementation for a Member
 */
public class MemoryMemberHeart extends AbstractMemberHeart implements MemberHeart, Linkable {

    private boolean completed = true;

    @Nullable
    private GroupHeart group;
    private Set<Rank> ranks = Collections.synchronizedSet(new THashSet<Rank>());
    private DateTime lastActive;
    private DateTime created;

    private final Member member;
    private final MemberPublisher memberPublisher;
    private final EventController events;
    private final Rank defaultRank;


    @Inject
    public MemoryMemberHeart(@Assisted Member member,
                             EventController events,
                             @Named("default-rank") Rank defaultRank,
                             MemberPublisher memberPublisher) {
        super(events);
        this.events = events;
        this.defaultRank = defaultRank;
        this.memberPublisher = memberPublisher;
        this.member = member;
        this.created = this.lastActive = DateTime.now();
    }

    public Member getHolder() {
        return member;
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

        if (result && linked()) {
            memberPublisher.publish(member);
        }
    }

    @Override
    public boolean removeRank(Rank rank) {
        if (getGroup() == null) {
            return false;
        }

        boolean result = ranks.remove(rank);

        if (result && linked()) {
            memberPublisher.publish(member);
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

        if (linked()) {
            memberPublisher.publish(member);
        }
    }

    @Override
    public DateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(DateTime created) {
        this.created = created;

        if (linked()) {
            memberPublisher.publish(member);
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

        if (linked()) {
            memberPublisher.publish(member);
        }

        if (group == null) {
            this.ranks.clear();
            events.publish(new MemberLeaveEvent(member, previous == null ? null : previous.getHolder()));
        } else {
            events.publish(new MemberJoinEvent(member));
        }

        if (group != null && !group.isMember(member)) {
            group.addMember(member);
        }
    }

    @Override
    public void unlink() {
        unlink(false);
    }

    @Override
    public boolean linked() {
        return completed;
    }

    @Override
    public void unlink(boolean value) {
        this.completed = value;
    }

    @Override
    public void link() {
        unlink(true);
    }
}
