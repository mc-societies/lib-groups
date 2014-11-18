package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Group;

/**
 * Represents a GroupDropPublisher
 */
public interface GroupDropPublisher {

    ListenableFuture<Group> drop(Group group);
}
