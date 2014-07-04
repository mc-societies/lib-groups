package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 *
 */
public interface Member extends Participant {

    UUID getUUID();

    Rank getRank();

    void setRank(Rank rank);

    @Nullable
    Group getGroup();

    void setGroup(@Nullable Group group);

    boolean isGroup(Group group);
}
