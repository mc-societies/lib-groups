package net.catharos.groups.request;

/**
 * Represents a AllAction
 */
public abstract class GlobalInvolved implements Involved {

    @Override
    public boolean isInvolved(Participant participant) {
        return true;
    }
}
