package com.demoapp.media.component.jwt;

import java.util.Map;

/**
 * Interface to implement a JWT encoder.
 *
 * @author Anton Pelykh
 */
public interface JwtEncoder {

    /**
     * Encode a JWT with the provided payload claims.
     *
     * @param payload payload claims
     * @return an encoded JWT token
     * @throws JwtManagerException exception in JWT manager
     */
    String encode(Map<String, Object> payload) throws JwtManagerException;
}
