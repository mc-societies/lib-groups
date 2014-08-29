package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.GroupStatePublisher;
import net.catharos.groups.publisher.LastActivePublisher;
import net.catharos.groups.publisher.NamePublisher;
import net.catharos.groups.publisher.SettingPublisher;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractPublishingSubject;
import net.catharos.groups.setting.target.SimpleTarget;
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


    private final UUID uuid;
    private String name, tag;
    private short state;
    private final NamePublisher namePublisher;
    private final LastActivePublisher lastactivePublisher;
    private final GroupStatePublisher groupStatePublisher;

    private DateTime lastActive;
    @Nullable
    private Group parent;

    private final THashMap<Group, Relation> relations = new THashMap<Group, Relation>();
    private final THashSet<Rank> ranks = new THashSet<Rank>();

    private final THashSet<Member> members = new THashSet<Member>();
    private final THashSet<Group> subGroups = new THashSet<Group>();


    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted("name") String name,
                        @Assisted("tag") String tag,
                        @Assisted @Nullable Group parent,
                        NamePublisher namePublisher,
                        LastActivePublisher lastactivePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher) {
        super(settingPublisher);
        this.uuid = uuid;
        this.name = name;
        this.tag = tag;
        this.namePublisher = namePublisher;
        this.lastactivePublisher = lastactivePublisher;
        this.groupStatePublisher = groupStatePublisher;
        setParent(parent);

        lastActive = DateTime.now();
    }

    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted("name") String name,
                        @Assisted("tag") String tag,
                        NamePublisher namePublisher,
                        LastActivePublisher lastactivePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher) {
        this(uuid, name, tag, null, namePublisher, lastactivePublisher, settingPublisher, groupStatePublisher);
    }

    @AssistedInject
    public DefaultGroup(@Assisted("name") String name,
                        @Assisted("tag") String tag,
                        Provider<UUID> uuidProvider,
                        NamePublisher namePublisher,
                        LastActivePublisher lastactivePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher) {
        this(uuidProvider
                .get(), name, tag, null, namePublisher, lastactivePublisher, settingPublisher, groupStatePublisher);
    }

    @Inject
    public DefaultGroup(Provider<UUID> uuidProvider,
                        NamePublisher namePublisher,
                        LastActivePublisher lastactivePublisher,
                        SettingPublisher settingPublisher,
                        GroupStatePublisher groupStatePublisher) {
        this(uuidProvider
                .get(), NEW_GROUP_NAME, NEW_GROUP_TAG, namePublisher, lastactivePublisher, settingPublisher, groupStatePublisher);
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
        short sstate = (short) state;

        if (this.state != sstate) {
            groupStatePublisher.publish(this, sstate);
        }

        this.state = sstate;
    }

    @Override
    public void setName(String name) {
        this.name = name;

        namePublisher.publish(this, name);
    }

    @Override
    public DateTime getLastActive() {
        return lastActive;
    }

    public void active() {
        this.lastActive = DateTime.now();

        lastactivePublisher.publish(this, lastActive);
    }

    @Override
    public Relation getRelation(Group anotherGroup) {
        Relation relation = relations.get(anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(this) : relation;
    }

    @Override
    public Collection<Relation> getRelations() {
        return relations.values();
    }

    @Override
    public void setRelation(Relation relation) {
        setRelation(relation, true);
    }

    @Override
    public void setRelation(Relation relation, boolean override) {
        setRelation(relation.getTarget(), relation, override);
    }

    @Override
    public void setRelation(Group target, Relation relation, boolean override) {
        if (!relation.contains(this)) {
            throw new IllegalArgumentException("Relation must contain a target or source identical to \"this\"!");
        }

        if (!override && !Objects.equal(relation, relations.get(target))) {
            throw new IllegalStateException("Relation already exists!");
        }

        relations.put(target, relation);

        if (!target.hasRelation(this)) {
            target.setRelation(this, relation, override);
        }
    }

    @Override
    public void removeRelation(Group anotherGroup) {
        if (hasRelation(anotherGroup)) {
            relations.remove(anotherGroup);
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    @Override
    public boolean hasRelation(Group anotherGroup) {
        return relations.containsKey(anotherGroup);
    }

    @Override
    public void addRank(Rank rank) {
        this.ranks.add(rank);
    }

    @Override
    public void removeRank(Rank rank) {
        //todo
    }

    @Override
    public Rank getRank(String name) {
        //todo
        return null;
    }

    @Override
    public Collection<Rank> getRanks() {
        return Collections.unmodifiableCollection(ranks);
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
            if (member.<Boolean>get(setting, null)) {
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
                "uuid=" + uuid +
                ", parent=" + parent +
                ", relations=" + relations +
                ", ranks=" + ranks +
                ", members=" + members +
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

        if (o == null || getClass() != o.getClass()) return false;

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
    public Collection<? extends Participant> getInvolved() {
        return members;
    }
}


