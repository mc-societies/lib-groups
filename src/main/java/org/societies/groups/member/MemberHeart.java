package org.societies.groups.member;

import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.societies.groups.Linkable;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.rank.Rank;

import java.util.Set;

/**
 * Represents a GroupMember
 */
public interface MemberHeart extends Linkable {

    Set<Rank> getRanks();

    void addRank(Rank rank);

    boolean hasRank(Rank rank);

    boolean removeRank(Rank rank);

    Rank getRank();

    DateTime getLastActive();

    void activate();

    void setLastActive(DateTime lastActive);

    DateTime getCreated();

    void setCreated(DateTime created);

    @Nullable
    GroupHeart getGroup();

    boolean hasGroup();

    boolean hasRule(String rule);

    void setGroup(@Nullable GroupHeart group);

    boolean isGroup(GroupHeart group);
}
