package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.Setting;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * Represents a GroupMember
 */
public interface MemberHeart {

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
    Group getGroup();

    boolean hasGroup();

    boolean hasRule(String rule);

    void setGroup(@Nullable Group group);

    boolean isGroup(Group group);
}
