package org.societies.groups;

/**
 * Represents a ExtensionFactory
 */
public interface ExtensionFactory<E, T> {

    E create(T target);
}
