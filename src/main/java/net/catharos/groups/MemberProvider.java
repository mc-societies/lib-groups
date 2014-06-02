package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;

/**
 * Represents a ParticipantProvider
 */
public interface MemberProvider<M extends Member> {

    ListenableFuture<M> getMember(UUID uuid);

    ListenableFuture<M> getMember(String name);
}
