package com.demoapp.media.module.recording.dto;

/**
 * @author Anton Pelykh
 */
public class RecordingStartRequestDto {

    /**
     * The recording type. The possible values: "AUDIO", "VIDEO", "SCREEN_SHARING"
     */
    private String recordingType;
    /**
     * The name of file where the recording need to be written
     */
    private String fileName;

    /**
     * Get the recording type
     *
     * @return the recording type. The possible values: "AUDIO", "VIDEO", "SCREEN_SHARING"
     */
    public String getRecordingType() {
        return recordingType;
    }

    /**
     * Set the recording type
     *
     * @param recordingType the recording type. The possible values: "AUDIO", "VIDEO", "SCREEN_SHARING"
     */
    public void setRecordingType(String recordingType) {
        this.recordingType = recordingType;
    }

    /**
     * Get the filename where the recording need to be written
     *
     * @return the filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the filename where the recording need to be written
     *
     * @param fileName the filename for the recording file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
