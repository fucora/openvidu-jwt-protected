package com.demoapp.media.component.security.web.authentication;

import com.demoapp.media.component.security.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of authentication filter for JWT.
 * </p>
 * This filter can handle a bearer token authentication.
 *
 * @author Anton Pelykh
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    private AuthenticationFailureHandler failureHandler = new HttpErrorMessageAuthenticationFailureHandler();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("Request to process authentication");
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication authResult;

        try {
            authResult = attemptAuthentication(request, response);
            if (authResult == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Subclass has indicated that it hasn't completed authentication");
                }

                // Return immediately as subclass has indicated that it hasn't completed
                // authentication.
                return;
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user", failed);

            handleUnsuccessfulAuthentication(request, response, failed);

            return;
        } catch (AuthenticationException failed) {
            handleUnsuccessfulAuthentication(request, response, failed);

            return;
        }

        handleSuccessfulAuthentication(request, response, filterChain, authResult);

        filterChain.doFilter(request, response);
    }

    private Authentication attemptAuthentication(HttpServletRequest request,
                                                 HttpServletResponse response) throws AuthenticationException {
        String authenticationToken = this.extractAuthenticationTokenFromRequest(request);

        if (authenticationToken == null) {
            throw new AuthenticationCredentialsNotFoundException(
                    "JWT token can not be extracted from Authorization header.");
        }

        return authenticationManager.authenticate(
                new JwtAuthenticationToken(authenticationToken));
    }

    private void handleSuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                FilterChain chain,
                                                Authentication authResult) {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    private void handleUnsuccessfulAuthentication(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication request failed: " + failed.toString(), failed);
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
            logger.debug("Delegating to authentication failure handler " + failureHandler);
        }

        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    private String extractAuthenticationTokenFromRequest(HttpServletRequest request) {
        String value = request.getHeader("Authorization");

        if (value == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authorization header value is empty");
            }

            return null;
        }

        String[] valueParts = value.trim().split("\\s+", 2);

        if (valueParts.length < 2 || !"Bearer".equals(valueParts[0])) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authorization scheme is not valid");
            }

            return null;
        }

        return valueParts[1];
    }
}
