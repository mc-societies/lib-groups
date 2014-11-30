package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.Group;

/**
 * Used to publish new groups to a data source and drop existing ones
 */
public interface GroupPublisher {

    /**
     * Publishes a new group to a data source
     *
     * @param group The group
     * @return A future which will fire when the group was published
     */
    ListenableFuture<Group> publish(Group group);
}
