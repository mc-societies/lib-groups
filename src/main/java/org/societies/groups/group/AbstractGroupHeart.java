package org.societies.groups.group;

import gnu.trove.set.hash.THashSet;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;

import java.util.Set;

/**
 * Represents a AbstractGroupHeart
 */
public abstract class AbstractGroupHeart implements GroupHeart {
    @Override
    public Rank getRank(String name) {
        for (Rank rank : getRanks()) {
            if (rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return getMembers().size();
    }

    @Override
    public Set<Member> getMembers(Setting<Boolean> setting) {
        THashSet<Member> out = new THashSet<Member>();

        for (Member member : getMembers()) {
            if (member.getBooleanRankValue(setting)) {
                out.add(member);
            }
        }

        return out;
    }
}
