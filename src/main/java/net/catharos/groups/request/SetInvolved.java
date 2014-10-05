package net.catharos.groups.request;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a SetInvolved
 */
public class SetInvolved implements Involved {

    private final Set<? extends Participant> participants;

    public SetInvolved(Set<? extends Participant> participants) {this.participants = participants;}

    @Override
    public boolean isInvolved(Participant participant) {
        return participants.contains(participant);
    }

    @Override
    public Collection<? extends Participant> getReceivers() {
        return participants;
    }
}
