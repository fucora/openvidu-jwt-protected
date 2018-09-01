package com.demoapp.media.component.jwt;

import java.util.Map;

/**
 * Interface to implement a JWT decoder.
 *
 * @author Anton Pelykh
 */
public interface JwtDecoder {

    /**
     * Decode a JWT token.
     *
     * @param token an encoded JWT token
     * @return payload claims. Currently looks like it's not the better way to use {@link Object}
     * as a base class for the claim value. So in future it makes sense to create an interface for
     * the claim implementation.
     * @throws JwtManagerException exception in JWT manager
     */
    Map<String, Object> decode(String token) throws JwtManagerException;

    /**
     * Verify a JWT token signature.
     *
     * Check if JWT signature is valid, and contains all the required claims.
     *
     * @param token an encoded JWT token
     * @return whether a JWT token is valid
     * @throws JwtManagerException exception in JWT manager
     */
    boolean verify(String token) throws JwtManagerException;
}
