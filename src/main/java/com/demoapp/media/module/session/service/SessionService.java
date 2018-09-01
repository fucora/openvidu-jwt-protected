package com.demoapp.media.module.session.service;

import com.demoapp.media.module.session.entity.InitializedSession;
import com.demoapp.media.module.session.entity.SessionInitializeProperties;
import io.openvidu.java.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to perform the operations on OpenVidu sessions. It's just OpenVidu
 * client wrapper with logging and errors handling.
 *
 * @author Anton Pelykh
 */
@Service
public class SessionService {

    private final Log logger = LogFactory.getLog(getClass());

    private OpenVidu openVidu;

    /**
     * The service constructor.
     *
     * @param openVidu the configured instance of OpenVidu client
     */
    @Autowired
    public SessionService(OpenVidu openVidu) {
        this.openVidu = openVidu;
    }

    /**
     * Create OpenVidu session.
     *
     * @param sessionInitializeProperties the info required to initialize a session
     * @return the session info
     * @throws SessionServiceException the unexpected error in the session service
     * which also have been logged. Mainly this means that things are going bad.
     */
    public InitializedSession create(SessionInitializeProperties sessionInitializeProperties) throws SessionServiceException {
        String token;
        try {
            SessionProperties sessionProperties = new SessionProperties.Builder()
                    .build();
            Session session = this.openVidu.createSession(sessionProperties);
            TokenOptions tokenOptions = new TokenOptions.Builder()
                    .role(OpenViduRole.PUBLISHER)
                    .build();
            token = session.generateToken(tokenOptions);
        } catch (OpenViduJavaClientException e) {
            logger.fatal("Unexpected error in OpenVidu client: " + e.getMessage(), e);

            throw new SessionServiceException("OpenVidu client error.", e);
        } catch (OpenViduHttpException e) {
            logger.fatal("Unexpected error in OpenVidu server: [" + e.getStatus() + "] " + e.getMessage(), e);

            throw new SessionServiceException("OpenVidu server error.", e);
        }

        return new InitializedSession(token);
    }
}
