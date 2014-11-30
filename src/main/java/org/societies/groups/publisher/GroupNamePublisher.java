package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.GroupHeart;

/**
 * Represents a NamePublisher
 */
public interface GroupNamePublisher {

    ListenableFuture<GroupHeart> publishName(GroupHeart group, String name);

    ListenableFuture<GroupHeart> publishTag(GroupHeart group, String tag);
}
