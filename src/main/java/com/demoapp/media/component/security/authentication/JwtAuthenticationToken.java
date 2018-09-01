package com.demoapp.media.component.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * An implementation of JWT authentication token.
 *
 * @author Anton Pelykh
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Map<String, Object> payload;

    /**
     * Creates a token without the array of authorities.
     *
     * This constructor can be safely used by any code that wishes to create a
     * <code>JwtAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     *
     * @param principal the JWT token which is mainly represented as string.
     */
    public JwtAuthenticationToken(Object principal) {
        super(null);

        this.principal = principal;
        this.payload = Collections.emptyMap();
        this.setAuthenticated(false);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     *
     * @param principal the JWT token which is mainly represented as string.
     * @param payload the JWT payload.
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     * represented by this authentication object.
     */
    public JwtAuthenticationToken(Object principal, Map<String, Object> payload, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        this.principal = principal;
        this.payload = payload != null ? payload : Collections.emptyMap();
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public Map<String, Object> getPayload() {
        return this.payload;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Can not set this token to trusted - use constructor which takes a GrantedAuthority list instead.");
        }

        super.setAuthenticated(false);
    }
}
