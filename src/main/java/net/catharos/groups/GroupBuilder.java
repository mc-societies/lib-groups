package net.catharos.groups;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.SettingException;
import net.catharos.groups.setting.target.Target;
import net.catharos.lib.shank.logging.InjectLogger;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

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

        group.complete(false);

        group.setCreated(created);

        //beautify
        for (Table.Cell<Setting, Target, String> cell : settings.cellSet()) {
            Setting setting = cell.getRowKey();
            Target target = cell.getColumnKey();
            String value = cell.getValue();

            try {
                group.set(setting, target, setting.convertFromString(group, target, value));
            } catch (SettingException e) {
                logger.warn("Failed to convert setting %s! Subject: %s Target: %s Value: %s", setting, group, target, value);
            }
        }

        group.complete();
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
