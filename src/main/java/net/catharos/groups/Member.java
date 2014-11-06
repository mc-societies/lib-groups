package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.lib.core.command.format.table.RowForwarder;
import net.catharos.lib.core.command.sender.Sender;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.UUID;

/**
 *
 */
public interface Member extends Participant, Sender, RowForwarder, Completable {

    UUID getUUID();

    @Override
    String getName();

    Set<Rank> getRanks();

    void addRank(Rank rank);

    DateTime getLastActive();

    void activate();

    void setLastActive(DateTime lastActive);

    DateTime getCreated();

    void setCreated(DateTime created);

    <V> V get(Setting<V> setting);

    @Nullable
    Group getGroup();

    boolean hasRule(String rule);

    void setGroup(@Nullable Group group);

    boolean isAvailable();

    boolean isGroup(Group group);
}
