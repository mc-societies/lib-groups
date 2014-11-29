package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.Setting;
import org.joda.time.DateTime;

/**
 * Represents a AbstractMemberHeart
 */
public abstract class AbstractMemberHeart implements MemberHeart {

    @Override
    public boolean hasRank(Rank rank) {
        return getRanks().contains(rank);
    }


    @Override
    public Rank getRank() {
        if (getGroup() == null) {
            return null;
        }

        Rank highest = null;

        for (Rank rank : getRanks()) {
            if (highest == null || rank.getPriority() > highest.getPriority()) {
                highest = rank;
            }
        }

        return highest;
    }


    @Override
    public <V> V getRankValue(Setting<V> setting) {
        for (Rank rank : getRanks()) {
            V value = rank.get(setting);

            if (value != null) {
                return value;
            }
        }

        return null;
    }

    @Override
    public boolean hasRule(String rule) {
        for (Rank rank : getRanks()) {
            if (rank.hasRule(rule)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean getBooleanRankValue(Setting<Boolean> setting) {
        Boolean value = getRankValue(setting);

        if (value == null) {
            return false;
        }

        return value;
    }

    @Override
    public void activate() {
        setLastActive(DateTime.now());
    }
}
