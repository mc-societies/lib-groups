package net.catharos.groups.request;

/**
 *
 */
public interface RequestResult {

    Request getRequest() throws RequestFailedException;

    Choice getChoice() throws RequestFailedException;
}
