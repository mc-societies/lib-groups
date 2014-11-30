package org.societies.groups.request;

import net.catharos.lib.core.command.Command;
import net.catharos.lib.core.command.sender.Sender;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a DefaultParticipant
 */
public class DefaultParticipant implements Participant {

    @Nullable
    private Request receivedRequest;
    @Nullable
    private Request suppliedRequest;

    private final Sender sender;

    public DefaultParticipant(Sender sender) {this.sender = sender;}

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
    public void send(String message) {
        sender.send(message);
    }

    @Override
    public void send(String message, Object... args) {
        sender.send(message, args);
    }

    @Override
    public void send(StringBuilder message) {
        sender.send(message);
    }

    @Override
    public boolean hasPermission(Command command) {
        return sender.hasPermission(command);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public String getName() {
        return sender.getName();
    }
}
