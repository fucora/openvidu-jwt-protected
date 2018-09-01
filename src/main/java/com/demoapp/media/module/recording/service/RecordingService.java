package com.demoapp.media.module.recording.service;

import com.demoapp.media.module.recording.entity.RecordingStartProperties;
import com.demoapp.media.module.recording.entity.StartedRecording;
import com.demoapp.media.module.recording.entity.StoppedRecording;
import io.openvidu.java.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to perform the operations on OpenVidu recordings. Currently it's just OpenVidu
 * client wrapper with logging and errors handling.
 *
 * @author Anton Pelykh
 */
@Service
public class RecordingService {

    private final Log logger = LogFactory.getLog(getClass());

    private OpenVidu openVidu;

    /**
     * The service constructor.
     *
     * @param openVidu the configured instance of OpenVidu client
     */
    @Autowired
    public RecordingService(OpenVidu openVidu) {
        this.openVidu = openVidu;
    }

    /**
     * Start the recording of the session
     *
     * @param sessionId the identifier of the session need to be recorded
     * @param recordingStartProperties the properties required to start the recording. All
     * the properties need to be safe (make sure they are not received from the user input)
     * @return the information about the recording that have been started. This information
     * can be provided to client
     */
    public StartedRecording start(String sessionId, RecordingStartProperties recordingStartProperties) {
        Recording recording;
        try {
            RecordingProperties recordingProperties = new RecordingProperties.Builder()
                    .name(recordingStartProperties.getFileName())
                    .build();
            recording = this.openVidu.startRecording(sessionId, recordingProperties);
        } catch (OpenViduJavaClientException e) {
            logger.fatal("Unexpected error in OpenVidu client: " + e.getMessage(), e);

            throw new RecordingServiceException("OpenVidu client error.", e);
        } catch (OpenViduHttpException e) {
            logger.fatal("Unexpected error in OpenVidu server: [" + e.getStatus() + "] " + e.getMessage(), e);

            throw new RecordingServiceException("OpenVidu server error.", e);
        }

        return new StartedRecording(
                recordingStartProperties.getRecordingType(),
                convertRecordingStatus(recording.getStatus()),
                recording.getId(),
                recording.getSessionId(),
                recording.getName(),
                recording.hasAudio(),
                recording.hasVideo(),
                recording.getCreatedAt());
    }

    /**
     * Stop the recording of the session
     *
     * @param recordingId the identifier of recording need to be stopped
     * @return the information about the recording that have been stopped. This information
     * can be provided to client
     */
    public StoppedRecording stop(String recordingId) {
        Recording recording;
        try {
            recording = openVidu.stopRecording(recordingId);
        } catch (OpenViduJavaClientException e) {
            logger.fatal("Unexpected error in OpenVidu client: " + e.getMessage(), e);

            throw new RecordingServiceException("OpenVidu client error.", e);
        } catch (OpenViduHttpException e) {
            logger.fatal("Unexpected error in OpenVidu server: [" + e.getStatus() + "] " + e.getMessage(), e);

            throw new RecordingServiceException("OpenVidu server error.", e);
        }

        return new StoppedRecording(
                null, // Right now just set the type to null
                convertRecordingStatus(recording.getStatus()),
                recording.getId(),
                recording.getSessionId(),
                recording.getName(),
                recording.getUrl(),
                recording.getSize(),
                recording.getDuration(),
                recording.hasAudio(),
                recording.hasVideo(),
                recording.getCreatedAt());
    }

    private com.demoapp.media.module.recording.entity.Recording.Status convertRecordingStatus(Recording.Status status) {
        switch (status) {
            case starting:
                return com.demoapp.media.module.recording.entity.Recording.Status.STARTING;
            case started:
                return com.demoapp.media.module.recording.entity.Recording.Status.STARTED;
            case stopped:
                return com.demoapp.media.module.recording.entity.Recording.Status.STOPPED;
            case available:
                return com.demoapp.media.module.recording.entity.Recording.Status.AVAILABLE;
            case failed:
                return com.demoapp.media.module.recording.entity.Recording.Status.FAILED;
            default:
                throw new IllegalArgumentException(
                        "Unable to convert the recording status. Consider to extend the method.");
        }
    }
}
