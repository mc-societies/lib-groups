package net.catharos.groups.request;

import net.catharos.lib.core.command.SystemSender;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a SimpleParticipant
 */
public class SimpleParticipant extends SystemSender implements Participant {

    @Nullable
    private Request receivedRequest, suppliedRequest;

    public SimpleParticipant() {
    }

    @Nullable
    @Override
    public Request getReceivedRequest() {
        return receivedRequest;
    }

    @Override
    public void setReceivedRequest(@Nullable Request receivedRequest) {
        this.receivedRequest = receivedRequest;
    }

    @Override
    @Nullable
    public Request getSuppliedRequest() {
        return suppliedRequest;
    }

    @Override
    public void setSuppliedRequest(@Nullable Request suppliedRequest) {
        this.suppliedRequest = suppliedRequest;
    }

    @Override
    public void clearReceivedRequest() {
        setReceivedRequest(null);
    }

    @Override
    public void clearSuppliedRequest() {
        setSuppliedRequest(null);
    }

    @Override
    public String getName() {
        return "simple";
    }
}
