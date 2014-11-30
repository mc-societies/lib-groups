package org.societies.groups.rank;

import com.google.common.primitives.Ints;
import org.jetbrains.annotations.NotNull;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.subject.DefaultSubject;

import java.util.Map;
import java.util.UUID;

/**
 * Represents a AbstractRank
 */
public abstract class AbstractRank extends DefaultSubject implements Rank {
    protected final UUID uuid;
    protected final String name;
    protected final int priority;
    private final Map<String, Setting<Boolean>> rules;

    public AbstractRank(UUID uuid, String name, int priority, Map<String, Setting<Boolean>> rules) {
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
        Setting<Boolean> setting = rules.get(rule);
        if (setting == null) {
            return;
        }

        set(setting, true);
    }

    @Override
    public boolean hasRule(String rule) {
        Setting<Boolean> setting = rules.get(rule);
        return setting != null && getBoolean(setting);
    }

    @Override
    public String getColumn(int column) {
        return name;
    }
}
