package net.catharos.groups.cache;

import net.catharos.groups.Member;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a MemberCache
 */
public interface MemberCache {

    @Nullable
    Member getMember(UUID uuid);

    @Nullable
    Member getMember(String name);

    Set<Member> getMembers();

    boolean cache(Member member);

    @Nullable
    Member clear(Member member);
}
