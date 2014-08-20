package net.catharos.groups.request;

/**
 * Represents a SimpleRequestMessenger
 */
public class SimpleRequestMessenger implements RequestMessenger {

    @Override
    public void start(Request<?> request, Participant participant) {
        participant.send("Request " + request + " started!");
    }

    @Override
    public void voted(Request<?> request, Participant participant) {
        participant.send(participant + " voted for " + request + "!");
    }

    @Override
    public void end(Request<?> request) {
        for (Participant participant : request.getInvolved()) {
            participant.send("Requests " + request + " finished!");
        }
    }
}
