package com.demoapp.media.module.recording.entity;

/**
 * The base class for the started/stopped recordings.
 *
 * @author Anton Pelykh
 */
public abstract class Recording {

    /**
     * The recording status. The status info is provided by OpenVidu server
     */
    public enum Status {

        /**
         * The recording is starting (cannot be stopped)
         */
        STARTING,

        /**
         * The recording has started and is going on
         */
        STARTED,

        /**
         * The recording has finished OK
         */
        STOPPED,

        /**
         * The recording is available for downloading. This status is reached for all
         * stopped recordings if
         * <a href="https://openvidu.io/docs/reference-docs/openvidu-server-params/"
         * target="_blank">OpenVidu Server configuration</a> property
         * <code>openvidu.recording.public-access</code> is true
         */
        AVAILABLE,

        /**
         * The recording has failed
         */
        FAILED,
    }

    /**
     * The recording type. The recording type is provided by main service
     */
    public enum Type {

        /**
         * The audio recording from microphone input
         */
        AUDIO,

        /**
         * The video recording from webcam input (with the audio track available)
         */
        VIDEO,

        /**
         * The video recording from screen sharing input (with the audio track available)
         */
        SCREEN_SHARING,
    }

    /**
     * The recording type
     */
    private final Recording.Type type;
    /**
     * The recording status
     */
    private final Recording.Status status;
    /**
     * The recording identifier
     */
    private final String id;
    /**
     * The session identifier
     */
    private final String sessionId;
    /**
     * The file name contains the recording
     */
    private final String fileName;
    /**
     * Whether the recording has audio track
     */
    private final boolean hasAudio;
    /**
     * Whether the recording has video track
     */
    private final boolean hasVideo;
    /**
     * Time when the recording started in UTC milliseconds
     */
    private final long createdAt;

    public Recording(Recording.Type type,
                     Recording.Status status,
                     String id,
                     String sessionId,
                     String fileName,
                     boolean hasAudio,
                     boolean hasVideo,
                     long createdAt) {
        this.type = type;
        this.status = status;
        this.id = id;
        this.sessionId = sessionId;
        this.fileName = fileName;
        this.hasAudio = hasAudio;
        this.hasVideo = hasVideo;
        this.createdAt = createdAt;
    }

    /**
     * Get the recording type
     *
     * @return the recording type
     */
    public Type getType() {
        return type;
    }

    /**
     * Get the recording status
     *
     * @return the recording status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Get the recording identifier
     *
     * @return the recording identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Get the session identifier
     *
     * @return the session identifier
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Get the file name contains the recording
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Whether the recording has an audio track
     *
     * @return <code>true</code> if the recording has an audio track, <code>false</code>
     * otherwise
     */
    public boolean hasAudio() {
        return hasAudio;
    }

    /**
     * Whether the recording has a video track
     *
     * @return <code>true</code> if the recording has a video track, <code>false</code>
     * otherwise
     */
    public boolean hasVideo() {
        return hasVideo;
    }

    /**
     * Get the time when the recording started in UTC milliseconds
     *
     * @return the time in UTC milliseconds
     */
    public long getCreatedAt() {
        return createdAt;
    }
}
