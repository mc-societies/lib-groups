package net.catharos.groups.request;

/**
 *
 */
public interface RequestMessenger {

    void start(Request<?> request, Participant participant);

    void voted(Request<?> request, Participant participant);

    void end(Request<?> request);
}
