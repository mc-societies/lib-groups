package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a ParticipantProvider
 */
public interface MemberCache {

    Member getMember(UUID uuid);

    Member getMember(String name);
}
