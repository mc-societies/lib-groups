package net.catharos.groups;

import com.google.inject.Inject;
import net.catharos.groups.rank.Rank;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Represents a GroupBuilder
 */
public class GroupBuilder {

    private final GroupFactory groupFactory;
    private UUID uuid;
    private String name, tag;
    private Group parent;

    private DateTime created;
    private short state;
    private Iterable<Rank> ranks;

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
            group = groupFactory.create(uuid, name, tag);

        } else {
            group = groupFactory.create(uuid, name, tag, parent);
        }

        group.setCreated(created);
        group.setState(state);

        for (Rank rank : ranks) {
            group.addRank(rank);
        }

        return group;
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
