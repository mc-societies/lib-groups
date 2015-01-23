package org.societies.groups;

import gnu.trove.map.hash.THashMap;
import net.catharos.lib.core.util.CastSafe;

/**
 * Represents a AbstractExtensible
 */
public abstract class AbstractExtensible implements Extensible {

    private final THashMap<Class<?>, Object> extensions = new THashMap<Class<?>, Object>();

    @Override
    public <E> E add(E extension) {
        return CastSafe.toGeneric(extensions.put(extension.getClass(), extension));
    }

    @Override
    public <E> E add(Class<? extends E> clazz, E extension) {
        extensions.put(clazz, extension);
        return CastSafe.toGeneric(extension);
    }

    @Override
    public <E> E get(Class<? extends E> extensionClass) {
        return CastSafe.toGeneric(extensions.get(extensionClass));
    }

    @Override
    public <E> boolean has(Class<? extends E> extensionClass) {
        return extensions.containsKey(extensionClass);
    }

    @Override
    public <E> boolean has(E extension) {
        return has(extension.getClass());
    }

    @Override
    public <E> E remove(Class<? extends E> extensionClass) {
        return CastSafe.toGeneric(extensions.remove(extensionClass));
    }

    @Override
    public <E> E remove(E extension) {
        return CastSafe.toGeneric(extensions.remove(extension.getClass()));
    }
}
