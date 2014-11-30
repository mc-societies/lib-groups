package org.societies.groups.group;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Used to create new instances {@link Group}
 */
public interface GroupFactory {

    Group create(String name, String tag);

    /**
     * Creates a group by name. The uuid will be generated.
     *
     * @param name The name
     * @return A brand new group
     */
    Group create(String name, String tag, DateTime created);

    /**
     * Creates a group by name and uuid.
     *
     * @param uuid The uuid
     * @param name The name
     * @return A brand new group
     */
    Group create(UUID uuid, String name, String tag);

    Group create(UUID uuid, String name, String tag, DateTime created);
}
