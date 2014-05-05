package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.subject.DefaultSubject;
import net.catharos.groups.setting.subject.Subject;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.*;

/**
 * Represents a Group
 */
public class Group extends DefaultSubject implements Subject {

    private final UUID uuid;
    @Nullable
    private Group parent;

    private final THashMap<Group, Relation> relations = new THashMap<Group, Relation>();
    private final THashSet<Rank> ranks = new THashSet<Rank>();

    private final ArrayList<Member> members = new ArrayList<Member>();
    private final THashSet<Group> subGroups = new THashSet<Group>();


    @AssistedInject
    public Group(@Assisted UUID uuid, @Assisted @Nullable Group parent) {
        this.uuid = uuid;
        this.parent = parent;
    }

    @AssistedInject
    public Group(@Assisted UUID uuid) {
        this(uuid, null);
    }

    @Inject
    public Group(Provider<UUID> uuidProvider) {
        this(uuidProvider.get());
    }

    public UUID getUUID() {
        return uuid;
    }

    public Relation getRelation(Group anotherGroup) {
        Relation relation = relations.get(anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(this) : relation;
    }

    public void setRelation(Relation relation) {
        setRelation(relation, true);
    }

    public void setRelation(Relation relation, boolean override) {
        setRelation(relation.getTarget(), relation, override);
    }

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

    public void removeRelation(Group anotherGroup) {
        if (hasRelation(anotherGroup)) {
            relations.remove(anotherGroup);
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    public boolean hasRelation(Group anotherGroup) {
        return relations.containsKey(anotherGroup);
    }

    public Collection<Rank> getRanks() {
        return Collections.unmodifiableCollection(ranks);
    }

    @Nullable
    public Group getParent() {
        return parent;
    }

    public Collection<Group> getSubGroups() {
        return subGroups;
    }

    public void addSubGroup(Group group) {
        if (group.getParent() != null) {
            throw new IllegalArgumentException("Group already has a parent!");
        }

        if (!subGroups.add(group)) {
            throw new IllegalArgumentException("Group is already sub group of \"this\" group!");
        }

        group.parent = this;
    }

    public boolean isParent(Group group) {
        return subGroups.contains(group);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public boolean isMember(Member participant) {
        return members.contains(participant);
    }

    public void addMember(Member participant) {
        if (participant.getGroups().contains(this)) {
            // Make sure he's not part of this group
            this.members.remove(participant);

            throw new IllegalStateException("Participant already is member of this group!");
        }

        this.members.add(participant);

        participant.addGroup(this);
    }
}
