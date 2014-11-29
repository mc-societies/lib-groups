package net.catharos.groups.setting.subject;

import java.util.UUID;

/**
 * Represents a DefaultSubject
 */
public class DefaultSubject extends AbstractSubject {
    private final UUID uuid;

    public DefaultSubject(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
