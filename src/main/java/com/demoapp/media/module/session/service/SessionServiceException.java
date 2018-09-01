package com.demoapp.media.module.session.service;

/**
 * Unexpected exception in the session service.
 *
 * @author Anton Pelykh
 */
public class SessionServiceException extends RuntimeException {

    public SessionServiceException(String message) {
        super(message);
    }

    public SessionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
