package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a ParticipantProvider
 */
public interface MemberProvider<M extends Member> {

    M getMember(UUID uuid);

    M getMember(String name);
}
