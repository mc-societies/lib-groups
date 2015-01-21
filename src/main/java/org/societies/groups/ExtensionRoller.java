package org.societies.groups;

/**
 * Represents a ExtensionRoller
 */
public interface ExtensionRoller<E extends Extensible> {

    void roll(E extensible);
}
