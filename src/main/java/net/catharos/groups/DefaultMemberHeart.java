package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.event.MemberJoinEvent;
import net.catharos.groups.event.MemberLeaveEvent;
import net.catharos.groups.rank.Rank;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Set;

/**
 * Default implementation for a Member
 */
public class DefaultMemberHeart extends AbstractMemberHeart implements MemberHeart {

    private final DefaultMember.Statics statics;
    private final Member member;

    @Nullable
    private Group group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private DateTime lastActive;
    private DateTime created;

    public DefaultMemberHeart(DefaultMember.Statics statics, Member member) {
        this.statics = statics;
        this.member = member;
        this.created = this.lastActive = DateTime.now();
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

        if (member.isCompleted()) {
            statics.publishGroup(member, group);
        }

        if (group == null) {
            this.ranks.clear();
            statics.publish(new MemberLeaveEvent(member, previous));
        } else {
            statics.publish(new MemberJoinEvent(member));
        }

        if (group != null && !group.isMember(member)) {
            group.addMember(member);
        }
    }


    @Override
    public boolean isGroup(Group group) {
        return this.group != null && this.group.equals(group);
    }
}
