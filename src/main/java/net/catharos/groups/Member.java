package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 *
 */
public interface Member extends Participant {

    UUID getUUID();

    Set<Rank> getRanks();

    void addRank(Rank rank);

    SettingValue get(Setting setting, Target target);

    @Nullable
    Group getGroup();

    void setGroup(@Nullable Group group);

    boolean isGroup(Group group);
}
