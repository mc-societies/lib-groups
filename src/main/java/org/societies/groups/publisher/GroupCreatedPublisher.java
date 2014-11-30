package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;
import org.societies.groups.group.GroupHeart;

/**
 * Represents a CreatedPublisher
 */
public interface GroupCreatedPublisher {

    ListenableFuture<GroupHeart> publishCreated(GroupHeart group, DateTime created);
}
