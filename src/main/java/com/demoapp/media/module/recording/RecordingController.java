package com.demoapp.media.module.recording;

import com.demoapp.media.module.recording.dto.*;
import com.demoapp.media.module.recording.entity.RecordingStartProperties;
import com.demoapp.media.module.recording.entity.StartedRecording;
import com.demoapp.media.module.recording.entity.StoppedRecording;
import com.demoapp.media.module.recording.service.RecordingService;
import com.demoapp.media.module.recording.service.RecordingServiceException;
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
 * Controller to manage OpenVidu recordings.
 *
 * @author Anton Pelykh
 */
@RestController
@RequestMapping(path = "/api/v1")
public class RecordingController extends AbstractController {

    private RecordingService recordingService;

    /**
     * @param recordingService the service to perform operations on the recording
     */
    @Autowired
    public RecordingController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    /**
     * Start a recording of the session.
     *
     * @param requestDto the request DTO which contains the data required to start recording
     * @param bindingResult the binding result
     * @return the response DTO which contains the data to start recording on client side
     */
    @RequestMapping(path = "/sessions/{sessionId}/recordings/start", method = { RequestMethod.POST })
    public ResponseEntity<RecordingStartResponseDto> start(@PathVariable(name = "sessionId") String sessionId,
                                                           @RequestBody @Valid RecordingStartRequestDto requestDto,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityHttpException(bindingResult);
        }

        RecordingAssembler assembler = new RecordingAssembler();

        StartedRecording startedRecording;
        try {
            RecordingStartProperties recordingStartProperties = assembler.writeRecordingStartProperties(requestDto);
            startedRecording = this.recordingService.start(sessionId, recordingStartProperties);
        } catch (RecordingServiceException e) {
            throw new InternalServerErrorHttpException(
                    "The recording can not be started because of internal service error.", e);
        }

        return new ResponseEntity<>(
                assembler.writeRecordingStartResponseDto(startedRecording),
                HttpStatus.CREATED);
    }

    /**
     * Stop a recording of the session.
     *
     * @param requestDto the request DTO which contains the data required to stop recording
     * @param bindingResult the binding result
     * @return the response DTO which contains the data need to be sent on client side
     */
    @RequestMapping(path = "/sessions/{sessionId}/recordings/{recordingId}/stop", method = { RequestMethod.PUT })
    public ResponseEntity<RecordingStopResponseDto> stop(@PathVariable(name = "sessionId") String sessionId,
                                                         @PathVariable(name = "recordingId") String recordingId,
                                                         @RequestBody @Valid RecordingStopRequestDto requestDto,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityHttpException(bindingResult);
        }

        RecordingAssembler assembler = new RecordingAssembler();

        StoppedRecording stoppedRecording;
        try {
            stoppedRecording = this.recordingService.stop(recordingId);
        } catch (RecordingServiceException e) {
            throw new InternalServerErrorHttpException(
                    "The recording can not be stopped because of internal service error.", e);
        }

        return new ResponseEntity<>(
                assembler.writeRecordingStopResponseDto(stoppedRecording),
                HttpStatus.OK);
    }
}
