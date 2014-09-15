package net.catharos.groups;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a MemberCache
 */
public interface MemberCache<M extends Member> {

    @Nullable
    M getMember(UUID uuid);

    @Nullable
    M getMember(String name);

    boolean cache(M member);

    @Nullable
    M clear(M member);
}
