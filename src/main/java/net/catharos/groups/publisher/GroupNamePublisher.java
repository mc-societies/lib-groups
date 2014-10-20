package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Group;

/**
 * Represents a NamePublisher
 */
public interface GroupNamePublisher {

    ListenableFuture<Group> publishName(Group group, String name);
}
