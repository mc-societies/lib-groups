package net.catharos.groups.cache;

import net.catharos.groups.Member;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a MemberCache
 */
public interface MemberCache<M extends Member> {

    @Nullable
    M getMember(UUID uuid);

    @Nullable
    M getMember(String name);

    Set<M> getMembers();

    boolean cache(M member);

    @Nullable
    M clear(M member);
}
