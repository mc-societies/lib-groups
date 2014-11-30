package org.societies.groups.group;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.lib.core.util.CastSafe;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.societies.groups.DefaultRelation;
import org.societies.groups.Relation;
import org.societies.groups.event.Event;
import org.societies.groups.event.EventController;
import org.societies.groups.event.GroupTagEvent;
import org.societies.groups.member.Member;
import org.societies.groups.publisher.*;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

/**
 * Represents a DefaultGroupHeart
 */
public class DefaultGroupHeart extends AbstractGroupHeart {

    private final Group group;

    private String name, tag;
    private DateTime created;

    private final THashMap<UUID, Rank> ranks = new THashMap<UUID, Rank>();

    private final THashSet<Member> members = new THashSet<Member>();

    private final Statics statics;

    public DefaultGroupHeart(Group group, Statics statics,
                             String name, String tag, DateTime created) {
        this.group = group;
        this.statics = statics;
        this.name = name;
        this.tag = tag;
        this.created = created;
    }

    @Override
    public Group getHolder() {
        return group;
    }

    @Override
    public UUID getUUID() {
        return group.getUUID();
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

        statics.publish(new GroupTagEvent(group));

        if (group.isCompleted()) {
            statics.publishTag(this, name);
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;

        if (group.isCompleted()) {
            statics.publishName(this, name);
        }
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
    public DateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(DateTime created) {
        this.created = created;

        if (group.isCompleted()) {
            statics.publishCreated(this, created);
        }
    }

    @Override
    public Relation getRelation(GroupHeart anotherGroup) {
        Relation relation = group.get(statics.getRelationSetting(), anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(group) : relation;
    }

    @Override
    public Collection<Relation> getRelations() {
        return unmodifiableCollection(CastSafe
                .<Collection<Relation>>toGeneric(group.getSettings().row(statics.getRelationSetting()).values()));
    }

    @Override
    public Collection<Relation> getRelations(Relation.Type type) {
        THashSet<Relation> relations = new THashSet<Relation>();
        for (Object obj : group.getSettings().row(statics.getRelationSetting()).values()) {
            Relation relation = (Relation) obj;
            relations.add(relation);

        }
        return relations;
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation) {
        setRelation(target, relation, true);
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation, boolean override) {
        if (!relation.contains(group)) {
            throw new IllegalArgumentException("Relation must contain a target or source identical to \"this\"!");
        }

        if (!relation.contains(target)) {
            throw new IllegalArgumentException("Relation contains wrong target!");
        }

        if (!override && !Objects.equal(relation, getRelation(target))) {
            throw new IllegalStateException("Relation already exists!");
        }

        if (target == this) {
            throw new IllegalStateException("Target and source mustn't be equal!");
        }


        group.set(statics.getRelationSetting(), target, relation);


        if (!target.hasRelation(this)) {
            target.setRelation(this, relation, override);
        }
    }

    @Override
    public void removeRelation(GroupHeart anotherGroup) {
        if (hasRelation(anotherGroup)) {
            group.remove(statics.getRelationSetting(), anotherGroup);
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    @Override
    public boolean hasRelation(GroupHeart anotherGroup) {
        Relation value = group.get(statics.getRelationSetting(), anotherGroup);

        if (value == null) {
            return false;
        }

        UUID opposite = value.getOpposite(group.getUUID());

        return opposite != null && opposite.equals(anotherGroup.getUUID());

    }

    @Override
    public void addRank(Rank rank) {
        Rank result = this.ranks.put(rank.getUUID(), rank);

        if (!rank.equals(result) && group.isCompleted()) {
            statics.publishRank(this, rank);
        }
    }

    @Override
    public void removeRank(Rank rank) {
        boolean result = this.ranks.remove(rank.getUUID()) != null;

        if (result && group.isCompleted()) {
            statics.drop(rank);
        }
    }

    @Override
    public Rank getRank(UUID uuid) {
        //beautify
        for (Rank defaultRank : statics.getDefaultRanks()) {
            if (defaultRank.getUUID().equals(uuid)) {
                return defaultRank;
            }
        }
        return this.ranks.get(uuid);
    }

    @Override
    public Collection<Rank> getRanks() {
        return CollectionUtils.union(statics.getDefaultRanks(), ranks.values());
    }

    @Override
    public Collection<Rank> getRanks(String rule) {
        THashSet<Rank> ranks = new THashSet<Rank>();

        for (Rank rank : getRanks()) {
            if (rank.hasRule(rule)) {
                ranks.add(rank);
            }
        }

        return ranks;
    }

    @Override
    public Set<Member> getMembers() {
        return unmodifiableSet(members);
    }

    @Override
    public Set<Member> getMembers(String rule) {
        Setting<Boolean> setting = statics.getRules().get(rule);

        if (setting == null) {
            return Collections.emptySet();
        }

        return getMembers(setting);
    }

    @Override
    public boolean isMember(Member member) {
        return getMembers().contains(member);
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
    public boolean isVerified() {
        return group.getBoolean(statics.getVerifySetting());
    }

    @Override
    public void verify(boolean newState) {
        group.set(statics.getVerifySetting(), newState);
    }


    public final static class Statics {
        private final SettingPublisher settingPublisher;
        private final GroupNamePublisher namePublisher;
        private final GroupRankPublisher groupRankPublisher;
        private final RankDropPublisher rankDropPublisher;
        private final GroupCreatedPublisher createdPublisher;

        private final Setting<Relation> relationSetting;
        private final Setting<Boolean> verifySetting;

        private final Map<String, Setting<Boolean>> rules;

        private final Set<Rank> defaultRanks;

        private final EventController eventController;

        @Inject
        public Statics(SettingPublisher settingPublisher, GroupNamePublisher namePublisher,
                       GroupRankPublisher groupRankPublisher,
                       RankDropPublisher rankDropPublisher,
                       GroupCreatedPublisher createdPublisher,
                       Setting<Relation> relationSetting,
                       @Named("verify") Setting<Boolean> verifySetting,
                       Map<String, Setting<Boolean>> rules,
                       @Named("predefined-ranks") Set<Rank> defaultRanks,
                       EventController eventController) {
            this.settingPublisher = settingPublisher;
            this.namePublisher = namePublisher;
            this.groupRankPublisher = groupRankPublisher;
            this.rankDropPublisher = rankDropPublisher;
            this.createdPublisher = createdPublisher;
            this.relationSetting = relationSetting;
            this.verifySetting = verifySetting;
            this.rules = rules;
            this.defaultRanks = defaultRanks;
            this.eventController = eventController;
        }

        public Map<String, Setting<Boolean>> getRules() {
            return rules;
        }

        public Setting<Boolean> getVerifySetting() {
            return verifySetting;
        }

        public Setting<Relation> getRelationSetting() {
            return relationSetting;
        }

        public Set<Rank> getDefaultRanks() {
            return defaultRanks;
        }

        public void publish(Event event) {
            eventController.publish(event);
        }

        public ListenableFuture<Rank> drop(Rank rank) {
            return rankDropPublisher.drop(rank);
        }

        public ListenableFuture<GroupHeart> publishRank(GroupHeart group, Rank rank) {
            return groupRankPublisher.publishRank(group, rank);
        }

        public ListenableFuture<GroupHeart> publishName(GroupHeart group, String name) {
            return namePublisher.publishName(group, name);
        }

        public ListenableFuture<GroupHeart> publishTag(GroupHeart group, String tag) {
            return namePublisher.publishTag(group, tag);
        }

        public ListenableFuture<GroupHeart> publishCreated(GroupHeart group, DateTime created) {
            return createdPublisher.publishCreated(group, created);
        }

        public SettingPublisher getSettingPublisher() {
            return settingPublisher;
        }
    }

}
