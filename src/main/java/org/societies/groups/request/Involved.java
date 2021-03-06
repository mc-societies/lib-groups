package org.societies.groups.request;

import java.util.Collection;

/**
 * Represents a Action
 */
public interface Involved {

    boolean isInvolved(Participant participant);

    Collection<? extends Participant> getRecipients();
}
