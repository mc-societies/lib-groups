package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Involved;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.lib.core.command.format.table.RowForwarder;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * This represents a generic group. A group can be a party, where {@link net.catharos.groups.Member}s participate, a town with citizens or a guild.
 * <p>
 * A group can have a parent and child groups.
 * <p>
 * {@link net.catharos.groups.Relation}s are bidirectional between groups. This means they are mirrored to each other.
 */
public interface Group extends Subject, RowForwarder, Involved, Completable {

    /**
     * The default name for new groups
     */
    String NEW_GROUP_NAME = "new-group";

    String NEW_GROUP_TAG = "ng";

    /**
     * @return The uuid
     */
    @Override
    UUID getUUID();

    /**
     * @return The name
     */
    String getName();

    String getTag();

    void setTag(String tag);

    /**
     * Sets the new name
     *
     * @param name The name
     */
    void setName(String name);

    /**
     * @return The last date this group showed any sign of living
     */
    DateTime getLastActive();

    DateTime getCreated();

    void setCreated(DateTime created);

    /**
     * @return The parent group of this group or null if there is no parent
     */
    @Nullable
    Group getParent();

    /**
     * Sets the parent group of this group
     *
     * @param group The group
     */
    void setParent(@Nullable Group group);

    /**
     * @return {@link #getParent()} != null
     */
    boolean hasParent();

    /**
     * @param group The group
     * @return Whether the group is the parent of this group
     */
    boolean isParent(Group group);

    /**
     * @param anotherGroup An other group
     * @return The relation to the other group
     */
    Relation getRelation(Group anotherGroup);

    Collection<Relation> getRelations();

    /**
     * Sets the relation to an other group
     *
     * @param target
     * @param relation The relation
     */
    void setRelation(Group target, Relation relation);

    /**
     * @param target   The target of the relation, this overrides the target specified in the relation object
     * @param relation The relation
     * @param override Whether we want to override a previous relation
     * @see #setRelation(Group, Relation)
     */
    void setRelation(Group target, Relation relation, boolean override);

    /**
     * Removes a relation to another group
     *
     * @param anotherGroup The relation
     */
    void removeRelation(Group anotherGroup);

    /**
     * @param anotherGroup An other group
     * @return Whether there is any relation to a group
     */
    boolean hasRelation(Group anotherGroup);

    void addRank(Rank rank);

    void removeRank(Rank rank);

    Rank getRank(String name);

    Rank getRank(UUID uuid);

    /**
     * @return The ranks of this group
     */
    Collection<Rank> getRanks();

    /**
     * @param rule The rule to look by
     * @return The ranks of this group
     */
    Collection<Rank> getRanks(String rule);

    /**
     * @return All sub groups of this group
     */
    Collection<Group> getSubGroups();

    /**
     * Adds a sub group
     *
     * @param group The sub group
     */
    void addSubGroup(Group group);

    /**
     * Removes a sub group
     *
     * @param group The sub group
     */
    void removeSubGroup(Group group);

    /**
     * @param group The group
     * @return Whether the group is a sub group of this group
     */
    boolean hasSubGroup(Group group);

    /**
     * @return All members of this group
     */
    Set<Member> getMembers();

    int size();

    /**
     * @param setting The setting to look by
     * @return The members which have this setting
     */
    public Set<Member> getMembers(Setting<Boolean> setting);

    /**
     * @param member The member
     * @return Whether the member is a member of this group
     */
    boolean isMember(Member member);

    /**
     * Adds a member to this group
     *
     * @param member The member
     */
    void addMember(Member member);

    /**
     * Removes a member from this group
     *
     * @param member The member
     */
    void removeMember(Member member);

    boolean isVerified();

    void verify(boolean newState);

    boolean isActive();
}
