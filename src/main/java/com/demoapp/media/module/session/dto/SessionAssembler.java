package com.demoapp.media.module.session.dto;

import com.demoapp.media.module.session.entity.InitializedSession;
import com.demoapp.media.module.session.entity.SessionInitializeProperties;

import java.util.Map;

/**
 * An assembler for the sessions.
 *
 * @author Anton Pelykh
 */
public class SessionAssembler {

    /**
     * Write an entity from the JWT payload
     *
     * @param payload the JWT payload
     * @return the properties required for the session initialization
     */
    public SessionInitializeProperties writeSessionInitializeProperties(Map<String, Object> payload) {
        throw new UnsupportedOperationException("Method not implemented.");
    }

    /**
     * Write an entity from the request DTO
     *
     * @param dto the request DTO
     * @return the properties required for the session initialization
     */
    public SessionInitializeProperties writeSessionInitializeProperties(SessionRequestDto dto) {
        return new SessionInitializeProperties();
    }

    /**
     * Write DTO from the properties required to access to session
     *
     * @param initializedSession the properties required to connect to the session
     * @return the response DTO object
     */
    public SessionAccessResponseDto writeSessionResponseDto(InitializedSession initializedSession) {
        SessionAccessResponseDto dto = new SessionAccessResponseDto();
        dto.setToken(initializedSession.getToken());

        return dto;
    }
}
