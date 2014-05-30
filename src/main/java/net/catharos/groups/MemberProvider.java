package net.catharos.groups;

import net.catharos.lib.core.concurrent.Future;

import java.util.UUID;

/**
 * Represents a ParticipantProvider
 */
public interface MemberProvider<M extends Member> {

    Future<M> getMember(UUID uuid);

    Future<M> getMember(String name);
}
