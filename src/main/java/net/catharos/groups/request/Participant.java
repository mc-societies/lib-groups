package net.catharos.groups.request;

import net.catharos.lib.core.command.sender.Sender;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Participant
 */
public interface Participant extends Sender {

    String getName();

    @Nullable
    Request getReceivedRequest();

    void setReceivedRequest(Request activeRequest);

    void clearReceivedRequest();

    @Nullable
    Request getSuppliedRequest();

    void setSuppliedRequest(@Nullable Request suppliedRequest);

    void clearSuppliedRequest();
}
