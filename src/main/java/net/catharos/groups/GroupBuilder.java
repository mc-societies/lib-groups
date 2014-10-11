package net.catharos.groups;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Represents a GroupBuilder
 */
public class GroupBuilder  {

    private final GroupFactory groupFactory;
    private UUID uuid;
    private String name, tag;
    private Group parent;

    private DateTime created;
    private short state;
    private Iterable<Rank> ranks;
    private final Table<Setting, Target, byte[]> settings = HashBasedTable.create();

    @Inject
    public GroupBuilder(GroupFactory groupFactory) {
        this.groupFactory = groupFactory;
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

    public GroupBuilder setParent(Group parent) {
        this.parent = parent;
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

    public Group getParent() {
        return parent;
    }

    public Group build() {
        Group group;
        if (parent == null) {
            group = groupFactory.create(uuid, name, tag, created);

        } else {
            group = groupFactory.create(uuid, name, tag, created, parent);
        }
        int previousState = group.getState();
        group.setState(DefaultGroup.PREPARE);

        group.setCreated(created);
        group.setState(state);

        for (Rank rank : ranks) {
            group.addRank(rank);
        }

        for (Table.Cell<Setting, Target, byte[]> cell : settings.cellSet()) {
            Setting setting = cell.getRowKey();
            Target target = cell.getColumnKey();
            group.set(setting, target, setting.convert(group, target, cell.getValue()));
        }

        group.setState(previousState);
        return group;
    }

    public void put(Setting rowKey, Target columnKey, byte[] value) {
        settings.put(rowKey, columnKey, value);
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Iterable<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(Iterable<Rank> ranks) {
        this.ranks = ranks;
    }
}
