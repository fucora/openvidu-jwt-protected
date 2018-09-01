package com.demoapp.media.component.jwt;

/**
 * This is a basic JWT manager exception. Currently it thrown if operation can not be performed due
 * some system problem, like: wrong configuration, wrong usage (e.g. you trying to decode a non verified token).
 * But if you need to build some exception hierarchy upon this, you're welcome to do this.
 *
 * @author Anton Pelykh
 */
public class JwtManagerException extends Exception {

    public JwtManagerException() {
        super();
    }

    public JwtManagerException(String message) {
        super(message);
    }

    public JwtManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtManagerException(Throwable cause) {
        super(cause);
    }
}
