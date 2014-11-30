package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;
import org.societies.groups.group.Group;

/**
 * Represents a CreatedPublisher
 */
public interface GroupCreatedPublisher {

    ListenableFuture<Group> publishCreated(Group group, DateTime created);
}
