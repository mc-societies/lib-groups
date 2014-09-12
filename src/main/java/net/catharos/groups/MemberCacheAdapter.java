package net.catharos.groups;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;

import static com.google.common.util.concurrent.Futures.immediateFuture;

/**
 * Represents a MemberCacheAdapter
 */
public class MemberCacheAdapter<M extends Member> implements MemberProvider<M> {

    private final MemberCache<M> memberCache;

    public MemberCacheAdapter(MemberCache<M> memberCache) {this.memberCache = memberCache;}

    @Override
    public ListenableFuture<M> getMember(UUID uuid) {
        return immediateFuture(memberCache.getMember(uuid));
    }

    @Override
    public ListenableFuture<M> getMember(String name) {
        return immediateFuture(memberCache.getMember(name));
    }
}
