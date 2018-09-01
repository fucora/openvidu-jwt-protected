package com.demoapp.media.module.session.entity;

/**
 * Properties required to get an access to initialized session.
 *
 * @author Anton Pelykh
 */
public class InitializedSession {

    private String token;

    /**
     * @param token the token to connect to session with
     */
    public InitializedSession(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
