package org.societies.groups.rank;

import org.societies.groups.Linkable;
import org.societies.groups.setting.Setting;

import java.util.Map;
import java.util.UUID;

/**
 * Represents a DefaultRank
 */
public class DefaultRank extends AbstractRank implements Linkable {

    private boolean completed = true;

    public DefaultRank(UUID uuid,
                       String name,
                       int priority,
                       Map<String, Setting<Boolean>> rules) {
        super(uuid, name, priority, rules);
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
}
