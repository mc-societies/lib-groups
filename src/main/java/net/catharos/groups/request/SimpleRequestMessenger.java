package net.catharos.groups.request;

/**
 * Represents a SimpleRequestMessenger
 */
public class SimpleRequestMessenger<C extends Choice> implements RequestMessenger<C> {

    @Override
    public void start(Request<C> request, Participant participant) {
        participant.send("Request " + request + " started!");
    }

    @Override
    public void voted(Request<C> request, C choice, Participant participant) {
        participant.send(participant + " voted " + choice + " for " + request + "!");
    }

    @Override
    public void end(Request<C> request) {
        for (Participant participant : request.getReceivers()) {
            participant.send("Requests " + request + " finished!");
        }
    }

    @Override
    public void cancelled(Request<C> request) {
        for (Participant participant : request.getReceivers()) {
            participant.send("Requests " + request + " cancelled!");
        }
    }
}
