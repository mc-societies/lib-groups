package org.societies.groups.member;

import java.util.UUID;

/**
 * Represents a MemberFactory
 */
public interface MemberFactory {

    Member create(UUID uuid);
}
