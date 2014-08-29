package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.lib.core.command.format.table.RowForwarder;
import net.catharos.lib.core.command.sender.Sender;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 *
 */
public interface Member extends Participant, Sender, RowForwarder {

    UUID getUUID();

    String getName();

    short getState();

    void setState(short state);

    Set<Rank> getRanks();

    void addRank(Rank rank);

    <V> V get(Setting<V> setting);

    <V> V get(Setting<V> setting, Target target);

    @Nullable
    Group getGroup();

    void setGroup(@Nullable Group group);

    boolean isGroup(Group group);
}
