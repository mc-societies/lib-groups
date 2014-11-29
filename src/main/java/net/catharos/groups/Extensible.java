package net.catharos.groups;

/**
 * Represents a Extensible
 */
public interface Extensible {

    /**
     * Adds a extension to a object
     *
     * @param extension the extension instance
     * @return The new extension which was assigned
     */
    <E> E add(E extension);

    /**
     * Adds a extension to a object
     *
     * @param extension the extension instance
     * @return The new extension which was assigned
     */
    <E> E add(Class<? extends E> clazz, E extension);

    /**
     * Gets a extension of a specific type
     *
     * @param extensionClass The class which represents the extension
     * @param <E>            The extension type
     * @return The extension of the specific type
     */
    <E> E get(Class<? extends E> extensionClass);

    /**
     * Checks whether this instance has a extension of the specified type
     *
     * @param extensionClass The type of the specified extension
     * @param <E>            The type of the extension
     * @return Whether this instance contains a extension of the specific type
     */
    <E> boolean has(Class<? extends E> extensionClass);

    /**
     * Checks whether this instance has a extension of the specified type
     *
     * @param extension The object to get the type from
     * @param <E>       The type of the extension
     * @return Whether this instance contains a extension of the specific type
     */
    <E> boolean has(E extension);

    /**
     * Removes a extension of the specified type
     *
     * @param extensionClass The type of the specified extension
     * @param <E>            The type of the extension
     * @return Removes a extension of the specified type
     */
    <E> E remove(Class<? extends E> extensionClass);

    /**
     * Removes a extension of the specified type
     *
     * @param extension The object to get the type from
     * @param <E>       The type of the extension
     * @return Removes a extension of the specified type
     */
    <E> E remove(E extension);
}
