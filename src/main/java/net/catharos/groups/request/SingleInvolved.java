package net.catharos.groups.request;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a SingularInvolved
 */
public class SingleInvolved implements Involved {
    private final Participant participant;

    public SingleInvolved(Participant participant) {this.participant = participant;}

    @Override
    public boolean isInvolved(Participant participant) {
        return participant.equals(this.participant);
    }

    @Override
    public Collection<? extends Participant> getRecipients() {
        return Collections.singleton(participant);
    }
}
