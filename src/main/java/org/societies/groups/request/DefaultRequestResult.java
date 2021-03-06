package org.societies.groups.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a RequestResult
 */
public class DefaultRequestResult<C extends Choice> implements RequestResult {

    private final C choice;
    private final Request request;

    public DefaultRequestResult(C choice, Request request) {
        this.choice = choice;
        this.request = request;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public C getChoice() {
        return choice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("choice", choice)
                .append("request", request)
                .toString();
    }
}
