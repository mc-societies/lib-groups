package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.*;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractPublishingSubject;
import net.catharos.groups.setting.target.SimpleTarget;
import net.catharos.lib.core.util.CastSafe;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import javax.inject.Inject;
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
    private short state;
    private final GroupNamePublisher namePublisher;
    private final GroupStatePublisher groupStatePublisher;
    private final GroupRankPublisher groupRankPublisher;
    private final RankDropPublisher rankDropPublisher;
    private final Setting<Relation> relationSetting;
    private final GroupCreatedPublisher createdPublisher;

    @Nullable
    private Group parent;

    private final THashMap<UUID, Rank> ranks = new THashMap<UUID, Rank>();

    private final THashSet<Member> members = new THashSet<Member>();
    private final THashSet<Group> subGroups = new THashSet<Group>();


    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted("name") String name,
                        @Assisted("tag") String tag,
                        @Assisted DateTime created,
                        @Assisted @Nullable Group parent,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher, Setting<Relation> relationSetting) {
        super(settingPublisher);
        this.uuid = uuid;
        this.name = name;
        this.tag = tag;
        this.created = created;
        this.namePublisher = namePublisher;
        this.groupStatePublisher = groupStatePublisher;
        this.groupRankPublisher = groupRankPublisher;
        this.rankDropPublisher = rankDropPublisher;
        this.createdPublisher = createdPublisher;
        this.relationSetting = relationSetting;
        setParent(parent);
    }

    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted("name") String name,
                        @Assisted("tag") String tag,
                        @Assisted DateTime created,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher,
                        Setting<Relation> relationSetting) {
        this(
                uuid, name, tag,
                created, null,
                namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, createdPublisher,
                relationSetting
        );
    }

    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted("name") String name,
                        @Assisted("tag") String tag,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher,
                        Setting<Relation> relationSetting) {
        this(
                uuid, name, tag,
                DateTime.now(), null,
                namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, createdPublisher,
                relationSetting
        );
    }

    @AssistedInject
    public DefaultGroup(@Assisted("name") String name,
                        @Assisted("tag") String tag,
                        Provider<UUID> uuidProvider,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher,
                        Setting<Relation> relationSetting) {
        this(
                uuidProvider.get(), name, tag,
                DateTime.now(), null,
                namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, createdPublisher,
                relationSetting
        );
    }

    @AssistedInject
    public DefaultGroup(@Assisted("name") String name,
                        @Assisted("tag") String tag,
                        @Assisted DateTime created,
                        Provider<UUID> uuidProvider,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher,
                        Setting<Relation> relationSetting) {
        this(
                uuidProvider.get(), name, tag,
                created, null,
                namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, createdPublisher,
                relationSetting
        );
    }

    @Inject
    public DefaultGroup(Provider<UUID> uuidProvider,
                        GroupNamePublisher namePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher,
                        GroupRankPublisher groupRankPublisher,
                        RankDropPublisher rankDropPublisher,
                        GroupCreatedPublisher createdPublisher,
                        Setting<Relation> relationSetting) {
        this(
                uuidProvider.get(), NEW_GROUP_NAME, NEW_GROUP_TAG,
                DateTime.now(),
                namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, createdPublisher,
                relationSetting
        );
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
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        short newState = (short) state;

        if (this.state != newState && isPrepared()) {
            groupStatePublisher.publish(this, newState);
        }

        this.state = newState;
    }

    @Override
    public void setName(String name) {
        this.name = name;

        if (isPrepared()) {
            namePublisher.publish(this, name);
        }
    }

    @Override
    protected boolean isPrepared() {
        return getState() != PREPARE;
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

        if (isPrepared()) {
            createdPublisher.publish(this, created);
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

        if (!rank.equals(result) && isPrepared()) {
            groupRankPublisher.publish(this, rank);
        }
    }

    @Override
    public void removeRank(Rank rank) {
        boolean result = this.ranks.remove(rank.getUUID()) != null;

        if (result && isPrepared()) {
            rankDropPublisher.drop(this, rank);
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
        return Collections.unmodifiableCollection(ranks.values());
    }

    @Override
    public Collection<Rank> getRanks(String permission) {
        return null;
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
    public Set<Member> getMembers(Setting<Boolean> setting) {
        THashSet<Member> out = new THashSet<Member>();

        for (Member member : members) {
            if (member.<Boolean>getSingle(setting)) {
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


