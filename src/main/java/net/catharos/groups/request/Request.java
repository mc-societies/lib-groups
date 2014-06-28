package net.catharos.groups.request;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;

/**
 * Represents a Request
 */
public interface Request<C extends Choice> extends Involved {

    void vote(Participant participant, C choice);

    boolean isPending();

    DateTime getDateCreated();

    ListenableFuture<SimpleRequestResult<C>> result();
}
