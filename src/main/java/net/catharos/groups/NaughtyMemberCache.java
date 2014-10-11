package net.catharos.groups;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a NaughtyMemberCache
 */
public class NaughtyMemberCache<M extends Member> implements MemberCache<M> {

    @Nullable
    @Override
    public M getMember(UUID uuid) {
        return null;
    }

    @Nullable
    @Override
    public M getMember(String name) {
        return null;
    }

    @Override
    public boolean cache(M member) {
        return false;
    }

    @Nullable
    @Override
    public M clear(M member) {
        return null;
    }
}