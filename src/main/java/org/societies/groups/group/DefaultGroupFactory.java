package org.societies.groups.group;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.joda.time.DateTime;
import org.societies.groups.setting.subject.CompletablePublishingSubject;

import java.util.UUID;

/**
 * Represents a DefaultGroupFactory
 */
public class DefaultGroupFactory implements GroupFactory {

    private final Provider<UUID> uuidProvider;
    private final DefaultGroupHeart.Statics statics;

    @Inject
    public DefaultGroupFactory(
            Provider<UUID> uuidProvider,
            DefaultGroupHeart.Statics statics) {
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
    public Group create(final UUID uuid, String name, String tag, DateTime created) {
        CompoundGroup group = new CompoundGroup(
                uuid);

        group.setGroupHeart(new DefaultGroupHeart(group, statics, name, tag, created));
        group.setSubject(new CompletablePublishingSubject(statics.getSettingPublisher(), group) {
            @Override
            public UUID getUUID() {
                return uuid;
            }
        });
        return group;
    }
}
