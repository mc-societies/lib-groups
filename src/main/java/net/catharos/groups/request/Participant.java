package net.catharos.groups.request;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a Participant
 */
public interface Participant {

    @Nullable
    Request getActiveRequest();

    void setActiveRequest(Request activeRequest);

    void send(String msg);

    boolean clearRequest();
}
