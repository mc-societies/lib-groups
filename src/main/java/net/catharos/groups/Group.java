package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.lib.core.command.format.table.RowForwarder;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public interface Group extends Subject, Inactivatable, RowForwarder {

    String NEW_GROUP_NAME = "new-group";


    UUID getUUID();

    String getName();


    @Nullable
    Group getParent();

    void setParent(@Nullable Group group);

    boolean hasParent();

    boolean isParent(Group group);


    Relation getRelation(Group anotherGroup);

    void setRelation(Relation relation);

    void setRelation(Relation relation, boolean override);

    void setRelation(Group target, Relation relation, boolean override);

    void removeRelation(Group anotherGroup);

    boolean hasRelation(Group anotherGroup);


    Collection<Rank> getRanks();


    Collection<Group> getSubGroups();

    void addSubGroup(Group group);

    void removeSubGroup(Group group);

    boolean hasSubGroup(Group group);


    List<Member> getMembers();

    boolean isMember(Member participant);

    void addMember(Member member);
}
