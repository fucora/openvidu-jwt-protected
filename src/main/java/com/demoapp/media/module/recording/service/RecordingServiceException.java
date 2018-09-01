package com.demoapp.media.module.recording.service;

/**
 * Unexpected exception in the recording service.
 *
 * @author Anton Pelykh
 */
public class RecordingServiceException extends RuntimeException{

    public RecordingServiceException(String message) {
        super(message);
    }

    public RecordingServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
