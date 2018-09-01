package com.demoapp.media.module.recording.entity;

/**
 * The session recording that have been stopped.
 *
 * @author Anton Pelykh
 */
public class StoppedRecording extends Recording {

    private final String fileUrl;
    private final long fileSize;
    private final double duration;

    /**
     * @param type the type of the recording
     * @param status the status of the recording
     * @param id the identifier of the recording
     * @param sessionId the session identifier that the recording belongs to
     * @param fileName the recording file name
     * @param fileUrl the recording file URL
     * @param fileSize the recording file fileSize
     * @param duration the recording duration
     * @param hasAudio whether the recording has an audio track
     * @param hasVideo whether the recording has a video track
     * @param createdAt the time when the recording started in UTC milliseconds
     */
    public StoppedRecording(Type type,
                            Status status,
                            String id,
                            String sessionId,
                            String fileName,
                            String fileUrl,
                            long fileSize,
                            double duration,
                            boolean hasAudio,
                            boolean hasVideo,
                            long createdAt) {
        super(type, status, id, sessionId, fileName, hasAudio, hasVideo, createdAt);

        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.duration = duration;
    }

    /**
     * Get the recording file URL
     *
     * @return the URL of file
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Get the recording file size
     *
     * @return the recording file size
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Get the recording duration
     *
     * @return the recording duration
     */
    public double getDuration() {
        return duration;
    }
}
