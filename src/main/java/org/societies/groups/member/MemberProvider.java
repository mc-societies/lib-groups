package org.societies.groups.member;

import java.util.Set;
import java.util.UUID;

/**
 * A {@link MemberProvider} represents a data source for {@link Member}s.
 * If a member does not exist in a external data source a new one will be created.
 */
public interface MemberProvider {

    /**
     * @param uuid The uuid to look up
     * @return A future of the member
     */
    Member getMember(UUID uuid);

    /**
     * @param name The name to look up
     * @return A future of the member
     */
    Member getMember(String name);

    Set<Member> getMembers();
}
