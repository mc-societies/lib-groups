package org.societies.groups.member.memory;

import org.joda.time.DateTime;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.member.MemberHeart;
import org.societies.groups.rank.Rank;

/**
 * Represents a AbstractMemberHeart
 */
public abstract class AbstractMemberHeart implements MemberHeart {

    private boolean completed = true;

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
    public boolean hasRule(String rule) {
        for (Rank rank : getRanks()) {
            if (rank.hasRule(rule) || rank.hasRule("*")) { //beautify
                return true;
            }
        }

        return false;
    }

    @Override
    public void activate() {
        setLastActive(DateTime.now());
    }

    @Override
    public boolean hasGroup() {
        return getGroup() != null;
    }

    @Override
    public boolean isGroup(GroupHeart group) {
        GroupHeart current = getGroup();
        return current != null && current.equals(group);
    }

    @Override
    public void unlink() {
        unlink(false);
    }

    @Override
    public boolean linked() {
        return completed;
    }

    public void unlink(boolean value) {
        this.completed = value;
    }

    @Override
    public void link() {
        unlink(true);
    }
}
