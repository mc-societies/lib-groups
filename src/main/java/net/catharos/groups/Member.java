package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;
import net.catharos.lib.core.command.sender.Sender;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 *
 */
public interface Member extends Participant, Sender {

    UUID getUUID();

    Set<Rank> getRanks();

    void addRank(Rank rank);

    SettingValue get(Setting setting, Target target);

    @Nullable
    Group getGroup();

    void setGroup(@Nullable Group group);

    boolean isGroup(Group group);
}
