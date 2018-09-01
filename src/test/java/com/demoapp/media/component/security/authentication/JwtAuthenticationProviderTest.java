package com.demoapp.media.component.security.authentication;

import com.demoapp.media.component.jwt.JwtManager;
import com.demoapp.media.component.jwt.JwtManagerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationProviderTest {

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Test
    public void authenticate() throws Exception {
        given(this.jwtManager.verify("token"))
                .willReturn(true);
        given(this.jwtManager.decode("token"))
                .willReturn(
                        new HashMap<String, Object>(1) {{
                            put("sub", 1);
                            put("role", "USER");
                        }});

        Authentication token = new JwtAuthenticationToken("token");
        Authentication trustedToken = this.jwtAuthenticationProvider.authenticate(token);

        assertTrue(trustedToken instanceof JwtAuthenticationToken);
        assertEquals("token", trustedToken.getPrincipal());
        assertFalse(trustedToken.getAuthorities().isEmpty());
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticateWithNotVerifiedToken() throws Exception {
        given(this.jwtManager.verify("not-verified-token"))
                .willReturn(false);

        Authentication token = new JwtAuthenticationToken("not-verified-token");
        this.jwtAuthenticationProvider.authenticate(token);
    }

    @Test(expected = AuthenticationServiceException.class)
    public void authenticateWithWrongJwtMangerConfiguration() throws Exception {
        given(this.jwtManager.verify("token"))
                .willThrow(new JwtManagerException());

        Authentication token = new JwtAuthenticationToken("token");
        this.jwtAuthenticationProvider.authenticate(token);
    }

    @Test
    public void support() {
        Authentication token = new JwtAuthenticationToken("token");
        boolean supports = this.jwtAuthenticationProvider.supports(token.getClass());

        assertTrue(supports);
    }
}