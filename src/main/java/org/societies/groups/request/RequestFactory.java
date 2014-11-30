package org.societies.groups.request;

import org.societies.groups.request.simple.Choices;

/**
 * Represents a RequestFactory
 */
public interface RequestFactory<C extends Choice> {


    Request<C> create(Participant supplier, Involved recipients, RequestMessenger<Choices> requestMessenger);
}
