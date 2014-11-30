package org.societies.groups.setting.target;

import java.util.UUID;

/**
 * Represents a SimpleTarget
 */
public class SimpleTarget implements Target {

    private final UUID uuid;

    public SimpleTarget(UUID uuid) {this.uuid = uuid;}

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Target)) {
            return false;
        }

        Target that = (Target) o;

        return uuid.equals(that.getUUID());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
