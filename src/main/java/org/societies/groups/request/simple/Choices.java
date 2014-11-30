package org.societies.groups.request.simple;

import org.societies.groups.request.Choice;

/**
 * Represents a Choices
 */
public enum Choices implements Choice {

    ACCEPT,
    DENY,
    ABSTAIN,
    CANCELLED;


    public boolean success() {
        return this == ACCEPT;
    }

}
