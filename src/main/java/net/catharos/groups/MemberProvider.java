package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.UUID;

/**
 * A {@link net.catharos.groups.MemberProvider} represents a data source for {@link net.catharos.groups.Member}s.
 * If a member does not exist in a external data source a new one will be created.
 */
public interface MemberProvider<M extends Member> {

    /**
     * @param uuid The uuid to look up
     * @return A future of the member
     */
    ListenableFuture<M> getMember(UUID uuid);

    ListenableFuture<M> getMember(UUID uuid, Group predefined, ListeningExecutorService service);

    /**
     * @param name The name to look up
     * @return A future of the member
     */
    ListenableFuture<M> getMember(String name);
}
