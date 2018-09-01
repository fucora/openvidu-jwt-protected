package com.demoapp.media.component.security.web.authentication;

import com.demoapp.media.component.security.authentication.JwtAuthenticationToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.FilterChain;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void doFilter() throws Exception {
        given(this.authenticationManager.authenticate(new JwtAuthenticationToken("token")))
                .willReturn(
                        new JwtAuthenticationToken(
                                "token",
                                Collections.emptyMap(),
                                Collections.emptyList()));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationFilter.doFilter(request, response, this.filterChain);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void doFilterWithoutAuthenticationCredentials() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer ");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationFilter.doFilter(request, response, this.filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }
}