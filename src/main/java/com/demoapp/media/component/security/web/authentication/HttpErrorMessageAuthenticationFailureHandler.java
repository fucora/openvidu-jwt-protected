package com.demoapp.media.component.security.web.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <tt>AuthenticationFailureHandler</tt> which send an error message to client via HTTP.
 * <p/>
 * This implementation shows an error message based on authentication exception.
 *
 * @author Anton Pelykh
 */
public class HttpErrorMessageAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        Map<String, Object> error = new HashMap<>();
        error.put("code", HttpStatus.UNAUTHORIZED.value());

        if (exception instanceof BadCredentialsException) {
            error.put("message", "Authentication failed because of bad credentials.");
        } else if (exception instanceof AuthenticationServiceException) {
            error.put("message", "Authentication failed because of internal service error.");
        } else {
            error.put("message", "Authentication failed.");
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        response.getWriter().flush();
    }
}
