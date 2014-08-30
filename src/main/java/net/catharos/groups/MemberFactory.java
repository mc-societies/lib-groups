package net.catharos.groups;

import java.util.UUID;

/**
 * Represents a MemberFactory
 */
public interface MemberFactory<M extends Member> {

    M create(UUID uuid);
}
