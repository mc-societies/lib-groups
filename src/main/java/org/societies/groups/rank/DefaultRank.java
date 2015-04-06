package org.societies.groups.rank;

import com.google.common.primitives.Ints;
import gnu.trove.set.hash.THashSet;
import org.jetbrains.annotations.NotNull;
import org.societies.groups.Linkable;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupPublisher;

import java.util.Collections;
import java.util.Set;

/**
 * Represents a DefaultRank
 */
public class DefaultRank implements Linkable, Rank {

    private final String name;
    private final int priority;
    private final Set<String> rules = new THashSet<String>();
    private final Group owner;

    private boolean completed = true;

    private final Set<String> availableRules;
    private GroupPublisher groupPublisher;

    public DefaultRank(String name,
                       int priority,
                       Group owner, Set<String> availableRules, GroupPublisher groupPublisher) {
        this.name = name;
        this.priority = priority;
        this.owner = owner;
        this.availableRules = availableRules;
        this.groupPublisher = groupPublisher;
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

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isSlave(Rank rank) {
        return getPriority() < rank.getPriority();
    }

    @Override
    public int compareTo(@NotNull Rank anotherRank) {
        return Ints.compare(getPriority(), anotherRank.getPriority());
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public void addRule(String rule) {

        if (!availableRules.contains(rule)) {
            return;
        }

        rules.add(rule);

        if (linked()) {
            groupPublisher.publish(owner);
        }
    }

    @Override
    public boolean hasRule(String rule) {
        return rules.contains(rule);
    }

    @Override
    public String getColumn(int column) {
        return name;
    }

    @Override
    public Set<String> getRules() {
        return Collections.unmodifiableSet(rules);
    }

    @Override
    public void removeRule(String rule) {
        rules.remove(rule);

        if (linked()) {
            groupPublisher.publish(owner);
        }
    }
}
