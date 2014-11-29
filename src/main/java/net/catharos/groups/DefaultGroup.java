package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.event.Event;
import net.catharos.groups.event.EventController;
import net.catharos.groups.event.GroupTagEvent;
import net.catharos.groups.publisher.*;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractPublishingSubject;
import net.catharos.groups.setting.target.SimpleTarget;
import net.catharos.lib.core.util.CastSafe;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;

/**
 * Default implementation for a Group
 */
public class DefaultGroup extends AbstractPublishingSubject implements Group {

    public static final short PREPARE = 0xFBE;

    private final UUID uuid;
    private String name, tag;
    private DateTime created;

    private boolean completed = true;

    private final THashMap<UUID, Rank> ranks = new THashMap<UUID, Rank>();

    private final THashSet<Member> members = new THashSet<Member>();

    private final Statics statics;

    public DefaultGroup(UUID uuid,
                        String name,
                        String tag,
                        DateTime created,
                        Statics statics) {
        super(statics.getSettingPublisher());
        this.uuid = uuid;
        this.name = name;
        this.tag = tag;
        this.created = created;
        this.statics = statics;
    }

    @Override
    public UUID getUUID() {
        return uuid;
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

        statics.publish(new GroupTagEvent(this));

        if (isCompleted()) {
            statics.publishTag(this, name);
        }
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
    public void setName(String name) {
        this.name = name;

        if (isCompleted()) {
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

        if (isCompleted()) {
            statics.publishCreated(this, created);
        }
    }

    @Override
    public Relation getRelation(Group anotherGroup) {
        Relation relation = get(statics.getRelationSetting(), anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(this) : relation;
    }

    @Override
    public Collection<Relation> getRelations() {
        return unmodifiableCollection(CastSafe
                .<Collection<Relation>>toGeneric(super.settings.row(statics.getRelationSetting()).values()));
    }

    @Override
    public Collection<Relation> getRelations(Relation.Type type) {
        THashSet<Relation> relations = new THashSet<Relation>();
        for (Object obj : super.settings.row(statics.getRelationSetting()).values()) {
            Relation relation = (Relation) obj;
            relations.add(relation);

        }
        return relations;
    }

    @Override
    public void setRelation(Group target, Relation relation) {
        setRelation(target, relation, true);
    }

    @Override
    public void setRelation(Group target, Relation relation, boolean override) {
        if (!relation.contains(this)) {
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


        set(statics.getRelationSetting(), target, relation);


        if (!target.hasRelation(this)) {
            target.setRelation(this, relation, override);
        }
    }

    @Override
    public void removeRelation(Group anotherGroup) {
        if (hasRelation(anotherGroup)) {
            remove(statics.getRelationSetting(), anotherGroup);
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    @Override
    public boolean hasRelation(Group anotherGroup) {
        Relation value = get(statics.getRelationSetting(), anotherGroup);

        if (value == null) {
            return false;
        }

        UUID opposite = value.getOpposite(this.getUUID());

        return opposite != null && opposite.equals(anotherGroup.getUUID());

    }

    @Override
    public void addRank(Rank rank) {
        Rank result = this.ranks.put(rank.getUUID(), rank);

        if (!rank.equals(result) && isCompleted()) {
            statics.publishRank(this, rank);
        }
    }

    @Override
    public void removeRank(Rank rank) {
        boolean result = this.ranks.remove(rank.getUUID()) != null;

        if (result && isCompleted()) {
            statics.drop(rank);
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
    public int size() {
        return members.size();
    }

    @Override
    public Set<Member> getMembers(Setting<Boolean> setting) {
        THashSet<Member> out = new THashSet<Member>();

        for (Member member : members) {
            if (member.getBooleanRankValue(setting)) {
                out.add(member);
            }
        }

        return out;
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
        return members.contains(member);
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
        return getBoolean(statics.getVerifySetting());
    }

    @Override
    public void verify(boolean newState) {
        set(statics.getVerifySetting(), newState);
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

        // Find DefaultGroups and SimpleTargets if they are hashed in a map
        if (o instanceof SimpleTarget) {
            if (((SimpleTarget) o).getUUID().equals(uuid)) {
                return true;
            }
        }

        if (!(o instanceof DefaultGroup)) {
            return false;
        }

        DefaultGroup that = (DefaultGroup) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public String getColumn(int column) {
        return getName();
    }

    @Override
    public boolean isInvolved(Participant participant) {
        return members.contains(participant);
    }

    @Override
    public Collection<? extends Participant> getRecipients() {
        return members;
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

        public ListenableFuture<Group> publishRank(Group group, Rank rank) {
            return groupRankPublisher.publishRank(group, rank);
        }

        public ListenableFuture<Group> publishName(Group group, String name) {
            return namePublisher.publishName(group, name);
        }

        public ListenableFuture<Group> publishTag(Group group, String tag) {
            return namePublisher.publishTag(group, tag);
        }

        public ListenableFuture<Group> publishCreated(Group group, DateTime created) {
            return createdPublisher.publishCreated(group, created);
        }

        public SettingPublisher getSettingPublisher() {
            return settingPublisher;
        }
    }
}


