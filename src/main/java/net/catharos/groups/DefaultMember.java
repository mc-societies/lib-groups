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
import java.util.UUID;

/**
 * Default implementation for a Member
 */
public abstract class DefaultMember extends MemoryMember {

    private final Statics statics;

    @Nullable
    private Group group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private DateTime lastActive;
    private DateTime created;

    public DefaultMember(UUID uuid, Statics statics) {
        super(uuid);
        this.statics = statics;
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

        if (result && isCompleted()) {
            statics.publishRank(this, rank);
        }
    }

    @Override
    public boolean removeRank(Rank rank) {
        if (getGroup() == null) {
            return false;
        }

        boolean result = ranks.remove(rank);

        if (result && isCompleted()) {
            statics.dropRank(this, rank);
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

        if (isCompleted()) {
            statics.publishLastActive(this, lastActive);
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
            statics.publishCreated(this, created);
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
            statics.publishGroup(this, group);
        }

        if (group == null) {
            this.ranks.clear();
            statics.publish(new MemberLeaveEvent(this, previous));
        } else {
            statics.publish(new MemberJoinEvent(this));
        }

        if (group != null && !group.isMember(this)) {
            group.addMember(this);
        }
    }


    @Override
    public boolean isGroup(Group group) {
        return this.group != null && this.group.equals(group);
    }
}
