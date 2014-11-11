package net.catharos.groups;

/**
 * Represents a Completable
 */
public interface Completable {

    boolean isCompleted();

    void complete(boolean value);

    void complete();
}
