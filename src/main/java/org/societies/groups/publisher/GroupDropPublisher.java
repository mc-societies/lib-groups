package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.Group;

/**
 * Represents a GroupDropPublisher
 */
public interface GroupDropPublisher {

    ListenableFuture<Group> drop(Group group);
}
