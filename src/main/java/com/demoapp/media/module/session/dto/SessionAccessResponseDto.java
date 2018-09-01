package com.demoapp.media.module.session.dto;

/**
 * The response DTO for the data required to get an access to a session.
 *
 * @author Anton Pelykh
 */
public class SessionAccessResponseDto {

    /**
     * The token to connect to a session with
     */
    private String token;

    public SessionAccessResponseDto() {
    }

    /**
     * @param token the token to connect to a session with
     */
    public SessionAccessResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
