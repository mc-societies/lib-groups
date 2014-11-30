package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.Group;

/**
 * Represents a NamePublisher
 */
public interface GroupNamePublisher {

    ListenableFuture<Group> publishName(Group group, String name);

    ListenableFuture<Group> publishTag(Group group, String tag);
}
