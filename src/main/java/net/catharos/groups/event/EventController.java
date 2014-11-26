package net.catharos.groups.event;

/**
 *
 */
public interface EventController {

    void publish(Event event);

    void subscribe(Object listener);
}
