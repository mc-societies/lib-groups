package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a MemberCache
 */
public interface MemberCache<M extends Member> {

    M getMember(UUID uuid);


    M getMember(String name);

    void cache(M member);

    void clear(M member);
}
