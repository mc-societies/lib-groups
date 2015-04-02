package org.societies.groups.rank;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a StaticAbstractRank
 */
public class StaticRank implements Rank {
    private final UUID uuid;
    private final String name;
    private final int priority;
    private final Iterable<String> rules;

    @Inject
    public StaticRank(@Assisted UUID uuid, @Assisted String name, @Assisted int priority, @Assisted Iterable<String> rules) {
        this.uuid = uuid;
        this.name = name;
        this.priority = priority;
        this.rules = rules;
    }

    @Override
    public UUID getUUID() {
        return uuid;
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
    public boolean isStatic() {
        return true;
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
        throw new NotImplementedException("You can not add rules!");
    }

    @Override
    public boolean hasRule(String rule) {
        return Iterables.contains(rules, rule);
    }

    @Override
    public String getColumn(int column) {
        return name;
    }

    @Override
    public Set<String> getAvailableRules() {
        return ImmutableSet.copyOf(rules);
    }

    @Override
    public void removeRule(String rule) {
        throw new NotImplementedException("You can not remove rules!");
    }

    @Override
    public boolean linked() {
        return true;
    }

    @Override
    public void unlink() {

    }

    @Override
    public void link() {

    }
}
