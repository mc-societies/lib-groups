package org.societies.groups.request;

/**
 *
 */
public interface RequestMessenger<C extends Choice> {

    void start(Request<C> request);

    void voted(Request<C> request, C choice, Participant participant);

    void end(Request<C> request, C choice);

    void cancelled(Request<C> request);
}
