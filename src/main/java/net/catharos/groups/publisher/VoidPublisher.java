package net.catharos.groups.publisher;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Represents a VoidPublisher
 */
public class VoidPublisher<T> implements Publisher<T> {

    @Override
    public ListenableFuture<T> update(T update) {
        return Futures.immediateCheckedFuture(update);
    }
}
