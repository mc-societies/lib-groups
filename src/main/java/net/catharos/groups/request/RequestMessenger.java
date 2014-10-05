package net.catharos.groups.request;

/**
 *
 */
public interface RequestMessenger<C extends Choice> {

    void start(Request<C> request, Participant participant);

    void voted(Request<C> request, C choice, Participant participant);

    void end(Request<C> request);

    void cancelled(Request<C> request);
}
