package org.societies.groups.group.memory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.societies.groups.Relation;
import org.societies.groups.event.EventController;
import org.societies.groups.event.GroupTagEvent;
import org.societies.groups.group.AbstractGroupHeart;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupPublisher;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.unmodifiableSet;

/**
 * Represents a DefaultGroupHeart
 */
public class MemoryGroupHeart extends AbstractGroupHeart {

    private String name, tag;
    private DateTime created;
    private final THashMap<UUID, Rank> ranks = new THashMap<UUID, Rank>();
    private final THashSet<Member> members = new THashSet<Member>();


    private final Group group;
    private final GroupPublisher groupPublisher;
    private final Set<Rank> defaultRanks;
    private final EventController events;

    @AssistedInject
    public MemoryGroupHeart(@Assisted Group group,
                            String name, String tag, DateTime created,
                            @Named("verify") Setting<Boolean> verifySetting, Setting<Relation> relationSetting,
                            Map<String, Setting<Boolean>> rules,
                            @Named("predefined-ranks") Set<Rank> defaultRanks,
                            EventController events,
                            GroupPublisher groupPublisher) {
        super(rules, verifySetting, relationSetting, group);
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
}
