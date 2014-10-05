package net.catharos.groups.request;

import com.google.common.util.concurrent.ListenableFuture;
import org.joda.time.DateTime;

/**
 * Represents a Request
 */
public interface Request<C extends Choice> extends Involved {

    String getName();

    void start();

    void vote(Participant participant, C choice);

    void cancel();

    boolean isPending();

    DateTime getDateCreated();

    ListenableFuture<DefaultRequestResult<C>> result();
}
