package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

/**
 *
 */
public interface GroupPublisher {

    ListenableFuture<Group> publish(Group group);
}
