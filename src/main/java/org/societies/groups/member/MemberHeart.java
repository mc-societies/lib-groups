package org.societies.groups.member;

import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.societies.groups.Unlinkable;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;

import java.util.Set;

/**
 * Represents a GroupMember
 */
public interface MemberHeart extends Unlinkable {

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

    <V> V getRankValue(Setting<V> setting);

    boolean getBooleanRankValue(Setting<Boolean> setting);

    @Nullable
    GroupHeart getGroup();

    boolean hasGroup();

    boolean hasRule(String rule);

    void setGroup(@Nullable GroupHeart group);

    boolean isGroup(GroupHeart group);
}
