package net.catharos.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Represents a Update
 */
public interface Publisher<T> {

    ListenableFuture<T> update(final T update);
}
