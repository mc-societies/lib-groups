package net.catharos.groups;

import com.google.common.base.Objects;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.*;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractPublishingSubject;
import net.catharos.groups.setting.target.SimpleTarget;
import net.catharos.lib.core.util.CastSafe;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * Default implementation for a Group
 */
public class DefaultGroup extends AbstractPublishingSubject implements Group {

    public static final short PREPARE = 0xFBE;

    private final UUID uuid;
    private String name, tag;
    private DateTime created;
    private boolean completed = false;
    private final GroupNamePublisher namePublisher;
    private final GroupRankPublisher groupRankPublisher;
    private final RankDropPublisher rankDropPublisher;

    private final GroupCreatedPublisher createdPublisher;

    private final Setting<Relation> relationSetting;
    private final Setting<Boolean> verifySetting;

    private final Set<Rank> defaultRanks;

    @Nullable
    private Group parent;

    private final THashMap<UUID, Rank> ranks = new THashMap<UUID, Rank>();

    private final THashSet<Member> members = new THashSet<Member>();
    private final THashSet<Group> subGroups = new THashSet<Group>();

    public DefaultGroup(UUID uuid,
                        String name,
                        String tag,
                        DateTime created,
                        @Nullable Group parent,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher,
                        Setting<Relation> relationSetting,
                        Setting<Boolean> verifySetting,
                        Set<Rank> defaultRanks) {
        super(settingPublisher);
        this.uuid = uuid;
        this.name = name;
        this.tag = tag;
        this.created = created;
        this.namePublisher = namePublisher;
        this.groupRankPublisher = groupRankPublisher;
        this.rankDropPublisher = rankDropPublisher;
        this.createdPublisher = createdPublisher;
        this.relationSetting = relationSetting;
        this.verifySetting = verifySetting;

        setParent(parent);

        this.defaultRanks = defaultRanks;
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

        if (isCompleted()) {
            namePublisher.publishTag(this, name);
        }
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void complete() {
        completed = true;
    }

    @Override
    public void setName(String name) {
        this.name = name;

        if (isCompleted()) {
            namePublisher.publishName(this, name);
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
            createdPublisher.publishCreated(this, created);
        }
    }

    @Override
    public Relation getRelation(Group anotherGroup) {
        Relation relation = get(relationSetting, anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(this) : relation;
    }

    @Override
    public Collection<Relation> getRelations() {
        return CastSafe.toGeneric(super.settings.row(relationSetting).values());
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


        set(relationSetting, target, relation);


        if (!target.hasRelation(this)) {
            target.setRelation(this, relation, override);
        }
    }

    @Override
    public void removeRelation(Group anotherGroup) {
        if (hasRelation(anotherGroup)) {
            remove(relationSetting, anotherGroup);
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    @Override
    public boolean hasRelation(Group anotherGroup) {
        Relation value = get(relationSetting, anotherGroup);

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
            groupRankPublisher.publishRank(this, rank);
        }
    }

    @Override
    public void removeRank(Rank rank) {
        boolean result = this.ranks.remove(rank.getUUID()) != null;

        if (result && isCompleted()) {
            rankDropPublisher.drop(rank);
        }
    }

    @Override
    public Rank getRank(String name) {
        for (Rank rank : ranks.values()) {
            if (rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }

    @Override
    public Rank getRank(UUID uuid) {
        return this.ranks.get(uuid);
    }

    @Override
    public Collection<Rank> getRanks() {
        return CollectionUtils.union(defaultRanks, ranks.values());
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
    @Nullable
    public Group getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nullable Group group) {
        if (group == null) {
            Group parent = getParent();

            if (parent != null) {
                parent.removeSubGroup(this);
            }

        } else {

            if (!group.hasSubGroup(this)) {
                group.addSubGroup(this);
            }
        }

        this.parent = group;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public Collection<Group> getSubGroups() {
        return subGroups;
    }

    @Override
    public void addSubGroup(Group group) {
        if (group.getParent() != null) {
            throw new IllegalArgumentException("Group already has a parent!");
        }

        if (!subGroups.add(group)) {
            throw new IllegalArgumentException("Group is already sub group of \"this\" group!");
        }

        group.setParent(this);
    }

    @Override
    public void removeSubGroup(Group group) {
        subGroups.remove(group);

        if (group.isParent(this)) {
            group.setParent(null);
        }
    }

    @Override
    public boolean hasSubGroup(Group group) {
        return subGroups.contains(group);
    }

    @Override
    public boolean isParent(Group group) {
        return equals(group.getParent());
    }

    @Override
    public Set<Member> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    @Override
    public int size() {
        return members.size();
    }

    @Override
    public Set<Member> getMembers(Setting<Boolean> setting) {
        THashSet<Member> out = new THashSet<Member>();

        for (Member member : members) {
            if (member.<Boolean>getRankValue(setting)) {
                out.add(member);
            }
        }

        return out;
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
        return getBoolean(verifySetting);
    }

    @Override
    public void verify(boolean newState) {
        set(verifySetting, newState);
    }

    @Override
    public String toString() {
        return "DefaultGroup{" +
                "uuid=" + getUUID() +
                ", parent=" + getParent() +
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
}


