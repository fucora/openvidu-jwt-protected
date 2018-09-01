package com.demoapp.media.module.recording.entity;

/**
 * The session recording that have been started.
 *
 * @author Anton Pelykh
 */
public class StartedRecording extends Recording {

    /**
     * @param type the type of the recording
     * @param status the status of the recording
     * @param id the identifier of the recording
     * @param sessionId the session identifier that the recording belongs to
     * @param fileName the recording file name
     * @param hasAudio whether the recording has an audio track
     * @param hasVideo whether the recording has a video track
     * @param createdAt the time when the recording started in UTC milliseconds
     */
    public StartedRecording(Type type,
                            Status status,
                            String id,
                            String sessionId,
                            String fileName,
                            boolean hasAudio,
                            boolean hasVideo,
                            long createdAt) {
        super(type, status, id, sessionId, fileName, hasAudio, hasVideo, createdAt);
    }
}
