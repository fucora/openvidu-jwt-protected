package com.demoapp.media.component.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyManagementException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JWT manager implementation based on <a href="https://github.com/auth0/java-jwt">Auth0's Java JWT</a> library.
 * RSA algorithm is used here for token signing and verification.
 *
 * @author Anton Pelykh
 */
public class Auth0JwtManager implements JwtManager {

    private final RSAPublicKey rsaPublicKey;
    private final RSAPrivateKey rsaPrivateKey;

    /**
     * This constructor should be used in the case you need only decode a token.
     */
    public Auth0JwtManager() {
        this(null,null);
    }

    /**
     * This constructor should be used in the case you need only verify a token.
     *
     * @param rsaPublicKey RSA public you would like to verify a token with
     */
    public Auth0JwtManager(RSAPublicKey rsaPublicKey) {
        this(rsaPublicKey, null);
    }

    /**
     * This constructor should be used in the case you need only encode a token.
     *
     * @param rsaPrivateKey RSA private key you would like to sign (encode) a token with
     */
    public Auth0JwtManager(RSAPrivateKey rsaPrivateKey) {
        this(null, rsaPrivateKey);
    }

    /**
     * You can perform all the operations with this constructor.
     *
     * @param rsaPublicKey RSA public you would like to verify a token with
     * @param rsaPrivateKey RSA private key you would like to sign (encode) a token with
     */
    public Auth0JwtManager(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    @Override
    public String encode(Map<String, Object> payload) throws JwtManagerException {
        try {
            this.throwExceptionIfPrivateKeyNotProvided(this.rsaPrivateKey);
        } catch (KeyManagementException e) {
            throw new JwtManagerException("Enable no encode a JWT token.", e.getCause());
        }

        String token;
        try {
            Algorithm algorithm = Algorithm.RSA256(null, this.rsaPrivateKey);
            token = this.buildAndSignToken(payload, algorithm);
        } catch (JWTCreationException e){
            throw new JwtManagerException("Token can not be encoded.", e.getCause());
        }

        return token;
    }

    @Override
    public boolean verify(String token) throws JwtManagerException {
        try {
            this.throwExceptionIfPublicKeyNotProvided(this.rsaPublicKey);
        } catch (KeyManagementException e) {
            throw new JwtManagerException("Enable to verify a JWT token.", e.getCause());
        }

        try {
            Algorithm algorithm = Algorithm.RSA256(this.rsaPublicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }

    @Override
    public Map<String, Object> decode(String token) throws JwtManagerException {
        DecodedJWT decodedToken;
        try {
            decodedToken = JWT.decode(token);
        } catch (JWTDecodeException e){
            throw new JwtManagerException("Provided token can not be decoded.", e.getCause());
        }

        return decodedToken.getClaims().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().as(Object.class)));
    }

    private void throwExceptionIfPublicKeyNotProvided(RSAPublicKey rsaPublicKey) throws KeyManagementException {
        if (null == rsaPublicKey) {
            throw new KeyManagementException("Public key not provided to decode/verify a JWT token.");
        }
    }

    private void throwExceptionIfPrivateKeyNotProvided(RSAPrivateKey rsaPrivateKey) throws KeyManagementException {
        if (null == rsaPrivateKey) {
            throw new KeyManagementException("Private key not provided to encode a JWT token.");
        }
    }

    private String buildAndSignToken(Map<String, Object> payload, Algorithm algorithm) {
        JWTCreator.Builder builder = JWT.create();
        payload.forEach((k, v) -> {
            if (v instanceof Integer) {
                builder.withClaim(k, (Integer) v);
            } else if (v instanceof String) {
                builder.withClaim(k, (String) v);
            } else {
                throw new UnsupportedOperationException(
                        "Type of the claim you provide can not be handled. " +
                        "Consider to extend the type handling functionality of Auth0JwtManager.");
            }
        });

        return builder.sign(algorithm);
    }
}
