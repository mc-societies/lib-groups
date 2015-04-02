package org.societies.groups.group.memory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.set.hash.THashSet;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.societies.groups.DefaultRelation;
import org.societies.groups.Relation;
import org.societies.groups.event.EventController;
import org.societies.groups.event.GroupTagEvent;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.group.GroupPublisher;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

/**
 * Represents a DefaultGroupHeart
 */
public class MemoryGroupHeart implements GroupHeart {

    private final Set<String> rules;
    private String name, tag;
    private DateTime created;
    private final Map<UUID, Rank> ranks = Collections.synchronizedMap(new THashMap<UUID, Rank>());
    private final Set<Member> members = Collections.synchronizedSet(new THashSet<Member>());
    private final THashMap<UUID, Relation> relations = new THashMap<UUID, Relation>();


    private final Group group;
    private final GroupPublisher groupPublisher;
    private final Set<Rank> defaultRanks;
    private final EventController events;
    private boolean completed = true;

    @AssistedInject
    public MemoryGroupHeart(@Assisted Group group,
                            String name, String tag, DateTime created,
                            Set<String> rules,
                            @Named("predefined-ranks") Set<Rank> defaultRanks,
                            EventController events,
                            GroupPublisher groupPublisher) {
        this.rules = rules;
        this.group = group;
        this.defaultRanks = defaultRanks;
        this.events = events;
        this.name = name;
        this.tag = tag;
        this.created = created;
        this.groupPublisher = groupPublisher;
    }

    @Override
    public Group getHolder() {
        return group;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;

        events.publish(new GroupTagEvent(group));

        if (linked()) {
            groupPublisher.publish(group);
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;

        if (linked()) {
            groupPublisher.publish(group);
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
            groupPublisher.publish(group);
        }
    }

    @Override
    public void addRank(Rank rank) {
        Rank result = this.ranks.put(rank.getUUID(), rank);

        if (!rank.equals(result) && linked()) {
            groupPublisher.publish(group);
        }
    }

    @Override
    public void removeRank(Rank rank) {
        boolean result = this.ranks.remove(rank.getUUID()) != null;

        if (result && linked()) {
            groupPublisher.publish(group);
        }
    }

    @Override
    public Rank getRank(UUID uuid) {
        //beautify
        for (Rank defaultRank : defaultRanks) {
            if (defaultRank.getUUID().equals(uuid)) {
                return defaultRank;
            }
        }
        return this.ranks.get(uuid);
    }

    @Override
    public Collection<Rank> getRanks() {
        return CollectionUtils.union(defaultRanks, ranks.values());
    }


    @Override
    public Set<Member> getMembers() {
        return unmodifiableSet(members);
    }

    @Override
    public void addMember(Member member) {
        this.members.add(member);

        member.setGroup(this);
    }

    @Override
    public void removeMember(Member member) {
        if (member.isGroup(this)) {
            this.members.remove(member);
            member.setGroup(null);
        }
    }

    @Override
    public void send(String message) {
        for (Member member : members) {
            member.send(message);
        }
    }

    @Override
    public void send(String message, Object... obj) {
        for (Member member : members) {
            member.send(message, obj);
        }
    }

    @Override
    public Rank getRank(String name) {
        for (Rank rank : getRanks()) {
            if (rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }

    @Override
    public Collection<Rank> getRanks(String rule) {
        THashSet<Rank> ranks = new THashSet<Rank>();

        for (Rank rank : getRanks()) {
            if (rank.hasRule(rule) || rank.hasRule("*")) {   //beautify
                ranks.add(rank);
            }
        }

        return ranks;
    }

    @Override
    public Set<Member> getMembers(String rule) {
        if (!rules.contains(rule)) {
            return Collections.emptySet();
        }

        THashSet<Member> members = new THashSet<Member>();

        for (Member member : getMembers()) {
            if (member.hasRule(rule) || member.hasRule("*")) {   //beautify
                members.add(member);
            }
        }

        return members;
    }

    @Override
    public int size() {
        return getMembers().size();
    }

    @Override
    public boolean isMember(Member member) {
        return getMembers().contains(member);
    }

    @Override
    public Relation getRelation(GroupHeart anotherGroup) {
        Relation relation = relations.get(anotherGroup.getUUID());
        return relation == null ? DefaultRelation.unknownRelation(getUUID()) : relation;
    }

    @Override
    public Collection<Relation> getRelations() {
        return unmodifiableCollection(relations.values());
    }

    @Override
    public Collection<Relation> getRelations(final Relation.Type type) {
        final THashSet<Relation> relations = new THashSet<Relation>();

        relations.forEach(new TObjectProcedure<Relation>() {
            @Override
            public boolean execute(Relation relation) {

                if (relation.getType() != type) {
                    return true;
                }
                relations.add(relation);
                return true;
            }
        });

        return relations;
    }

    @Override
    public void setRawRelation(UUID anotherGroup, Relation relation) {
        relations.put(anotherGroup, relation);
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation) {
        setRelation(target, relation, true);
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation, boolean override) {
        if (!relation.contains(getUUID())) {
            throw new IllegalArgumentException("Relation must contain a target or source identical to \"this\"!");
        }

        if (!relation.contains(target)) {
            throw new IllegalArgumentException("Relation contains wrong target!");
        }

        if (!override && !com.google.common.base.Objects.equal(relation, getRelation(target))) {
            throw new IllegalStateException("Relation already exists!");
        }

        if (target == this) {
            throw new IllegalStateException("Target and source mustn't be equal!");
        }


        relations.put(target.getUUID(), relation);

        if (linked()) {
            groupPublisher.publish(group);
        }

        if (!target.hasRelation(this)) {
            target.setRelation(this, relation, override);
        }
    }

    @Override
    public void removeRelation(GroupHeart anotherGroup) {
        if (hasRelation(anotherGroup)) {
            relations.remove(anotherGroup.getUUID());

            if (linked()) {
                groupPublisher.publish(group);
            }
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    @Override
    public boolean hasRelation(GroupHeart anotherGroup) {
        Relation value = relations.get(anotherGroup.getUUID());

        if (value == null) {
            return false;
        }

        UUID opposite = value.getOpposite(getUUID());

        return opposite != null && opposite.equals(anotherGroup.getUUID());

    }

    @Override
    public DateTime getLastActive() {
        DateTime lastActive = null;

        for (Member member : getMembers()) {
            DateTime memberLastActive = member.getLastActive();
            if (lastActive == null || memberLastActive.isAfter(lastActive)) {
                lastActive = memberLastActive;
            }
        }

        if (lastActive == null) {
            return new DateTime(0);
        }

        return lastActive;
    }

    @Override
    public UUID getUUID() {
        return getHolder().getUUID();
    }

    @Override
    public void unlink() {
        unlink(false);
    }

    @Override
    public boolean linked() {
        return completed;
    }

    public void unlink(boolean value) {
        this.completed = value;
    }

    @Override
    public void link() {
        unlink(true);
    }

    @Override
    public String toString() {
        return "DefaultGroup{" +
                "uuid=" + getUUID() +
                ", relations=" + getRelations() +
                ", ranks=" + getRanks() +
                ", members=" + getMembers() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupHeart that = (GroupHeart) o;

        return getUUID().equals(that.getUUID());
    }

    @Override
    public int hashCode() {
        return getUUID().hashCode();
    }
}
