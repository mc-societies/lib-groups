package net.catharos.groups.cache;

import net.catharos.groups.Member;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a NaughtyMemberCache
 */
public class NaughtyMemberCache implements MemberCache {

    @Nullable
    @Override
    public Member getMember(UUID uuid) {
        return null;
    }

    @Nullable
    @Override
    public Member getMember(String name) {
        return null;
    }

    @Override
    public Set<Member> getMembers() {
        return null;
    }

    @Override
    public boolean cache(Member member) {
        return false;
    }

    @Nullable
    @Override
    public Member clear(Member member) {
        return null;
    }
}
