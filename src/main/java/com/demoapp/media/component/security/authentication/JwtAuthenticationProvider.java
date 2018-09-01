package com.demoapp.media.component.security.authentication;

import com.demoapp.media.component.jwt.JwtManager;
import com.demoapp.media.component.jwt.JwtManagerException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * JWT authentication implementation.
 *
 * @author Anton Pelykh
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private JwtManager jwtManager;

    public JwtAuthenticationProvider(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwtToken = authentication.getPrincipal().toString();

        Map<String, Object> payload;
        try {
            if (!this.jwtManager.verify(jwtToken)) {
                throw new BadCredentialsException("JWT token verification not passed.");
            }

            payload = this.jwtManager.decode(jwtToken);
        } catch (JwtManagerException e) {
            throw new AuthenticationServiceException("Error occurred while trying to verify a JWT token.", e);
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>(1);
        if (payload.get("role") != null) {
            // Not so reliable code.
            String role = "ROLE_" + payload.get("role");
            grantedAuthorities.add(
                    new SimpleGrantedAuthority(role));
        }

        return new JwtAuthenticationToken(
                jwtToken,
                payload,
                grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
