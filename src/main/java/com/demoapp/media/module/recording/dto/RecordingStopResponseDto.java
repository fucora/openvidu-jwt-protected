package com.demoapp.media.module.recording.dto;

/**
 * @author Anton Pelykh
 */
public class RecordingStopResponseDto {

    /**
     * The recording identifier
     */
    private String id;
    /**
     * The session identifier
     */
    private String sessionId;
    /**
     * The recording type
     */
    private String recordingType;
    /**
     * The recording status
     */
    private String recordingStatus;
    /**
     * The file name contains the recording
     */
    private String fileName;
    /**
     * The URL of file which contains the recording
     */
    private String fileUrl;
    /**
     * The file size
     */
    private long fileSize;
    /**
     * The duration of recording
     */
    private double duration;
    /**
     * Whether the recording has audio track
     */
    private boolean hasAudio;
    /**
     * Whether the recording has video track
     */
    private boolean hasVideo;
    /**
     * Time when the recording started in UTC milliseconds
     */
    private long createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(String recordingType) {
        this.recordingType = recordingType;
    }

    public String getRecordingStatus() {
        return recordingStatus;
    }

    public void setRecordingStatus(String recordingStatus) {
        this.recordingStatus = recordingStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
