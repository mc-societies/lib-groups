package org.societies.groups.group;

import org.joda.time.DateTime;
import org.societies.groups.Relation;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a GroupHearth
 */
public interface GroupHeart extends Target {

    Group getHolder();

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
     * @param anotherGroup An other group
     * @return The relation to the other group
     */
    Relation getRelation(GroupHeart anotherGroup);

    Collection<Relation> getRelations();

    Collection<Relation> getRelations(Relation.Type type);

    /**
     * Sets the relation to an other group
     *
     * @param target
     * @param relation The relation
     */
    void setRelation(GroupHeart target, Relation relation);

    /**
     * @param target   The target of the relation, this overrides the target specified in the relation object
     * @param relation The relation
     * @param override Whether we want to override a previous relation
     * @see #setRelation(GroupHeart, Relation)
     */
    void setRelation(GroupHeart target, Relation relation, boolean override);

    /**
     * Removes a relation to another group
     *
     * @param anotherGroup The relation
     */
    void removeRelation(GroupHeart anotherGroup);

    /**
     * @param anotherGroup An other group
     * @return Whether there is any relation to a group
     */
    boolean hasRelation(GroupHeart anotherGroup);

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
     * @return All members of this group
     */
    Set<Member> getMembers();

    int size();

    /**
     * @param setting The setting to look by
     * @return The members which have this setting
     */
    Set<Member> getMembers(Setting<Boolean> setting);

    Set<Member> getMembers(String rule);

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
}
