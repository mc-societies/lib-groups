package org.societies.groups.member;

import org.joda.time.DateTime;
import org.societies.groups.event.EventController;
import org.societies.groups.event.MemberJoinEvent;
import org.societies.groups.event.MemberLeaveEvent;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;

/**
 * Represents a AbstractMemberHeart
 */
public abstract class AbstractMemberHeart implements MemberHeart {

    private boolean completed = true;

    private final EventController events;

    protected AbstractMemberHeart(EventController events) {this.events = events;}

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
            if (rank.hasRule(rule) || rank.hasRule("*")) { //beautify
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

    @Override
    public boolean hasGroup() {
        return getGroup() != null;
    }

    @Override
    public boolean isGroup(GroupHeart group) {
        GroupHeart current = getGroup();
        return current != null && current.equals(group);
    }

    protected void publishMemberEvents(Member member, GroupHeart group, GroupHeart previous) {
        if (group == null) {
            events.publish(new MemberLeaveEvent(member, previous == null ? null : previous.getHolder()));
        } else {
            events.publish(new MemberJoinEvent(member));
        }
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
