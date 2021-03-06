package org.societies.groups.group;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Used to publish new groups to a data source and drop existing ones
 */
public interface GroupPublisher {

    /**
     * Publishes a new group to a data source
     *
     * @param uuid    The uuid
     * @param name    The name
     * @param tag     The tag
     * @param created The time this group has been created
     * @return A future which will fire when the group was published
     */
    Group publish(UUID uuid, String name, String tag, DateTime created);

    Group publish(String name, String tag);

    Group publish(Group group);

    Group destruct(Group group);
}
