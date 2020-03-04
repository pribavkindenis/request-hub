package com.pribavkindenis.requesthub.exception;

public class RequestNotBelongToUserException extends RuntimeException {

    public static final String MESSAGE_TEMPLATE = "Request with id %d does not belong to user with id %d";

    public RequestNotBelongToUserException(Long requestId, Long userId) {
        super(String.format(MESSAGE_TEMPLATE, requestId, userId));
    }
}
