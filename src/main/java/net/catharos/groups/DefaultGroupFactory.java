package net.catharos.groups;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Represents a DefaultGroupFactory
 */
public class DefaultGroupFactory implements GroupFactory {

    private final Provider<UUID> uuidProvider;
    private final DefaultGroup.Statics statics;

    @Inject
    public DefaultGroupFactory(
            Provider<UUID> uuidProvider,
            DefaultGroup.Statics statics) {
        this.uuidProvider = uuidProvider;
        this.statics = statics;
    }

    @Override
    public Group create(String name, String tag) {
        return create(uuidProvider.get(), name, tag);
    }

    @Override
    public Group create(String name, String tag, DateTime created) {
        return create(uuidProvider.get(), name, tag, created);
    }

    @Override
    public Group create(UUID uuid, String name, String tag) {
        return create(uuid, name, tag, DateTime.now());
    }

    @Override
    public Group create(UUID uuid, String name, String tag, DateTime created) {
        return new DefaultGroup(
                uuid, name, tag, created,
                statics);
    }
}
