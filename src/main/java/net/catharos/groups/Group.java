package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.subject.Subject;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public interface Group extends Subject {
    UUID getUUID();

    Relation getRelation(Group anotherGroup);

    void setRelation(Relation relation);

    void setRelation(Relation relation, boolean override);

    void setRelation(Group target, Relation relation, boolean override);

    void removeRelation(Group anotherGroup);

    boolean hasRelation(Group anotherGroup);

    Collection<Rank> getRanks();

    @Nullable
    Group getParent();

    Collection<Group> getSubGroups();

    void addSubGroup(DefaultGroup group);

    boolean isParent(Group group);

    List<Member> getMembers();

    boolean isMember(Member participant);

    void addMember(Member member);
}
