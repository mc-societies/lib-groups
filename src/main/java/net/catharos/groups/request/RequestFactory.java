package net.catharos.groups.request;

/**
 * Represents a RequestFactory
 */
public interface RequestFactory<C extends Choice> {


    Request<C> create(Participant supplier, String name, Involved recipients);
}
