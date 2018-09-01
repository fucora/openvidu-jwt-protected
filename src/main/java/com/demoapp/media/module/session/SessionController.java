package com.demoapp.media.module.session;

import com.demoapp.media.module.session.dto.SessionAccessResponseDto;
import com.demoapp.media.module.session.dto.SessionAssembler;
import com.demoapp.media.module.session.dto.SessionRequestDto;
import com.demoapp.media.module.session.entity.InitializedSession;
import com.demoapp.media.module.session.entity.SessionInitializeProperties;
import com.demoapp.media.module.session.service.SessionService;
import com.demoapp.media.module.session.service.SessionServiceException;
import com.demoapp.media.component.http.InternalServerErrorHttpException;
import com.demoapp.media.component.http.UnprocessableEntityHttpException;
import com.demoapp.media.component.web.servlet.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller to manage OpenVidu sessions.
 * <p/>
 * Simply put, session is the WebSocket connection we can connect to by using received token.
 *
 * @author Anton Pelykh
 */
@RestController
@RequestMapping(path = "/api/v1")
public class SessionController extends AbstractController {

    private SessionService sessionService;

    /**
     * @param sessionService the service to perform the operations on the sessions
     */
    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Initialize a session.
     * <p/>
     * You should use this endpoint to initialize a session and receive a token
     * you can connect to WebRTC with and start recording.
     *
     * @param requestDto the request DTO which contains the data required to create a session
     * @param bindingResult the binding result
     * @return the response DTO which contains the data required to connect to session on
     * client side and start recording
     * @throws UnprocessableEntityHttpException if the user input is invalid
     * @throws InternalServerErrorHttpException if an internal service error occurred
     *
     * @see <a href="https://openvidu.io/docs/reference-docs/REST-API/#post-apisessions">OpenVidu REST API docs</a>
     */
    @RequestMapping(path = "/sessions", method = { RequestMethod.POST })
    public ResponseEntity<SessionAccessResponseDto> create(@RequestBody @Valid SessionRequestDto requestDto,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityHttpException(bindingResult);
        }

        SessionAssembler assembler = new SessionAssembler();

        InitializedSession initializedSession;
        try {
            SessionInitializeProperties sessionInitializeProperties = assembler.writeSessionInitializeProperties(requestDto);
            initializedSession = this.sessionService.create(sessionInitializeProperties);
        } catch (SessionServiceException e) {
            throw new InternalServerErrorHttpException(
                    "Session can not be created because of internal service error.", e);
        }

        return new ResponseEntity<>(
                assembler.writeSessionResponseDto(initializedSession),
                HttpStatus.CREATED);
    }

    /**
     * Delete a session.
     * <p/>
     * You should use this endpoint to close the session. But currently it's not necessary because
     * OpenVidu does this automatically.
     *
     * @param id the session identifier
     * @return response with an empty body means that the session have been deleted
     *
     * @see <a href="https://openvidu.io/docs/reference-docs/REST-API/#delete-apisessionssession_id">OpenVidu REST API docs</a>
     * @see <a href="https://groups.google.com/forum/m/#!msg/openvidu/hyzKMS7IzrY/PJkbJ_fFAQAJ">Dead sessions protection</a>
     */
    @RequestMapping(path = "/sessions/{id}", method = { RequestMethod.DELETE })
    public ResponseEntity delete(@PathVariable(name = "id") String id) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
