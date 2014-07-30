package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.Publisher;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.AbstractSubject;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.*;

/**
 * Default implementation for a Group
 */
public class DefaultGroup extends AbstractSubject implements Group {


    private final UUID uuid;
    private String name;
    private final Publisher<Group> namePublisher;
    private final Publisher<Group> lastactivePublisher;
    private DateTime lastActive;
    @Nullable
    private Group parent;

    private final THashMap<Group, Relation> relations = new THashMap<Group, Relation>();
    private final THashSet<Rank> ranks = new THashSet<Rank>();

    private final ArrayList<Member> members = new ArrayList<Member>();
    private final THashSet<Group> subGroups = new THashSet<Group>();


    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted String name,
                        @Assisted @Nullable Group parent,
                        @Named("name-publisher") Publisher<Group> namePublisher,
                        @Named("lastactive-publisher") Publisher<Group> lastactivePublisher) {
        this.uuid = uuid;
        this.name = name;
        this.namePublisher = namePublisher;
        this.lastactivePublisher = lastactivePublisher;
        setParent(parent);

        lastActive = DateTime.now();
    }

    @AssistedInject
    public DefaultGroup(@Assisted UUID uuid,
                        @Assisted String name,
                        @Named("name-publisher") Publisher<Group> namePublisher,
                        @Named("lastactive-publisher") Publisher<Group> lastactivePublisher) {
        this(uuid, name, null, namePublisher, lastactivePublisher);
    }

    @AssistedInject
    public DefaultGroup(@Assisted String name,
                        Provider<UUID> uuidProvider,
                        @Named("name-publisher") Publisher<Group> namePublisher,
                        @Named("lastactive-publisher") Publisher<Group> lastactivePublisher) {
        this(uuidProvider.get(), name, null, namePublisher, lastactivePublisher);
    }

    @Inject
    public DefaultGroup(Provider<UUID> uuidProvider,
                        @Named("name-publisher") Publisher<Group> namePublisher,
                        @Named("lastactive-publisher") Publisher<Group> lastactivePublisher) {
        this(uuidProvider.get(), NEW_GROUP_NAME, namePublisher, lastactivePublisher);
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
    public void setName(String name) {
        this.name = name;
        this.namePublisher.update(this);
    }

    @Override
    public DateTime getLastActive() {
        return lastActive;
    }

    public void active() {
        this.lastActive = DateTime.now();
        this.lastactivePublisher.update(this);
    }

    @Override
    public Relation getRelation(Group anotherGroup) {
        Relation relation = relations.get(anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(this) : relation;
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
    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    @Override
    public Set<Member> getMembers(Setting setting) {
        THashSet<Member> out = new THashSet<Member>();

        for (Member member : members) {
            if (member.get(setting, null).booleanValue()) {
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
        if (member.isGroup(this)) {
            // Make sure he's not part of this group
            this.members.remove(member);

            throw new IllegalStateException("Participant already is member of this group!");
        }

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


