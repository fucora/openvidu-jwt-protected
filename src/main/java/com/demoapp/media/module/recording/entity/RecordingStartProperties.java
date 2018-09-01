package com.demoapp.media.module.recording.entity;

/**
 * The properties required to start the session recording.
 *
 * @author Anton Pelykh
 */
public class RecordingStartProperties {

    private String fileName;
    private Recording.Type recordingType;

    /**
     * Get the file name where the recording need to be written
     *
     * @return the name of the video/audio file that the recording need to be written into
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the file name where the recording need to be written
     *
     * @param fileName the name of the video/audio file that the recording need to be written into
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get the recording type. Knowing the type, it's probably possible to use
     * some optimization techniques
     *
     * @return the recording type
     */
    public Recording.Type getRecordingType() {
        return recordingType;
    }

    /**
     * Set the recording type. Knowing the type, it's probably possible to use
     * some optimization techniques
     *
     * @param recordingType the recording type
     */
    public void setRecordingType(Recording.Type recordingType) {
        this.recordingType = recordingType;
    }
}
