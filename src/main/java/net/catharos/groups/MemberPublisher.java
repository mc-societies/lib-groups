package net.catharos.groups;

import net.catharos.lib.core.concurrent.Future;

/**
 *
 */
public interface MemberPublisher<M extends Member> {

    Future<M> publish(M member);
}
