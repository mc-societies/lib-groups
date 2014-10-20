package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import net.catharos.groups.Group;
import org.joda.time.DateTime;

/**
 * Represents a CreatedPublisher
 */
public interface GroupCreatedPublisher {

    ListenableFuture<Group> publishCreated(Group group, DateTime created);
}
