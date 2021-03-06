package org.societies.groups.request;

import order.sender.Sender;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Participant
 */
public interface Participant extends Sender {

    @Nullable
    Request getReceivedRequest();

    void setReceivedRequest(Request activeRequest);

    void clearReceivedRequest();

    @Nullable
    Request getSuppliedRequest();

    void setSuppliedRequest(@Nullable Request suppliedRequest);

    void clearSuppliedRequest();
}
