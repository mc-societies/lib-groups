package org.societies.groups.event;

/**
 *
 */
public interface EventController {

    void publish(Event event);

    void subscribe(Object listener);
}
