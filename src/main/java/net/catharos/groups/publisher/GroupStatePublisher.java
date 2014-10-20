package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Group;

/**
 * Represents a MemberStatePublisher
 */
public interface GroupStatePublisher {

    ListenableFuture<Group> publishState(Group group, short state);
}
