package org.societies.groups.group;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.shank.logging.InjectLogger;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

import java.util.UUID;

/**
 * Represents a GroupBuilder
 */
public class GroupBuilder {

    private final GroupFactory groupFactory;
    private final Provider<UUID> uuidProvider;

    private UUID uuid;
    private String name, tag;

    private DateTime created;
    private final Table<Setting, Target, String> settings = HashBasedTable.create();

    @InjectLogger
    private Logger logger;

    @Inject
    public GroupBuilder(GroupFactory groupFactory, Provider<UUID> uuidProvider) {
        this.groupFactory = groupFactory;
        this.uuidProvider = uuidProvider;
    }

    public GroupBuilder setUUID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public GroupBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GroupBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public Group build() {
        if (uuid == null) {
            uuid = uuidProvider.get();
        }

        Group group = groupFactory.create(uuid, name, tag, created);


        group.unlink();

        group.setCreated(created);

        Setting.set(settings, group, logger);

        group.link();
        return group;
    }

    public void put(Setting rowKey, Target columnKey, String value) {
        settings.put(rowKey, columnKey, value);
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public Table<Setting, Target, String> getSettings() {
        return settings;
    }
}
