package org.societies.groups.cache;

import org.jetbrains.annotations.Nullable;
import org.societies.groups.member.Member;

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
