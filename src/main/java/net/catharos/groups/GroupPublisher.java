package net.catharos.groups;

import net.catharos.lib.core.concurrent.Future;

/**
 *
 */
public interface GroupPublisher {

    Future<Group> publish(Group group);
}
