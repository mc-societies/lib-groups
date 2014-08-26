package net.catharos.groups.setting.target;

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
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTarget that = (SimpleTarget) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
