package net.catharos.groups.request;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;

/**
 * Represents a Request
 */
public interface Request extends Involved {

    void vote(Participant participant, Choice choice);

    boolean isPending();

    DateTime getDateCreated();

    ListenableFuture<SimpleRequestResult> result();
}
