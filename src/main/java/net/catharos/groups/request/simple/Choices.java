package net.catharos.groups.request.simple;

import net.catharos.groups.request.Choice;

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
