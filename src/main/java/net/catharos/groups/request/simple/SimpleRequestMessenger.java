package net.catharos.groups.request.simple;

import net.catharos.groups.request.Choice;
import net.catharos.groups.request.Participant;
import net.catharos.groups.request.Request;
import net.catharos.groups.request.RequestMessenger;

/**
 * Represents a SimpleRequestMessenger
 */
public class SimpleRequestMessenger<C extends Choice> implements RequestMessenger<C> {

    @Override
    public void start(Request<C> request) {
        for (Participant participant : request.getRecipients()) {
            participant.send("Request " + request + " started!");
        }
    }

    @Override
    public void voted(Request<C> request, C choice, Participant participant) {
        participant.send(participant + " voted " + choice + " for " + request + "!");
    }

    @Override
    public void end(Request<C> request, C choice) {
        for (Participant participant : request.getRecipients()) {
            participant.send("Requests " + request + " finished!");
        }
    }

    @Override
    public void cancelled(Request<C> request) {
        for (Participant participant : request.getRecipients()) {
            participant.send("Requests " + request + " cancelled!");
        }
    }
}
