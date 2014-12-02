package org.societies.groups.group;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Represents a GroupDropPublisher
 */
public interface GroupDestructor {

    ListenableFuture<Group> destruct(Group group);
}
