package net.catharos.groups.rank;

import net.catharos.groups.setting.subject.Subject;

import java.util.UUID;

/**
 * Represents a Rank
 */
public interface Rank extends Comparable<Rank>, Subject {

    int DEFAULT_PRIORITY = 0;

    @Override
    UUID getUUID();

    String getName();

    int getPriority();

    int getState();

    void setState(int state);

    boolean isSlave(Rank rank);

}
