package com.demoapp.media.module.recording.dto;

import com.demoapp.media.module.recording.entity.RecordingStartProperties;
import com.demoapp.media.module.recording.entity.StartedRecording;
import com.demoapp.media.module.recording.entity.StoppedRecording;
import com.demoapp.media.module.recording.entity.Recording;

/**
 * An assembler for the recording.
 *
 * @author Anton Pelykh
 */
public class RecordingAssembler {

    /**
     * Write the entity from the provided request DTO.
     * </p>
     * The entity can be provided to business logic layer.
     *
     * @param dto the request DTO
     * @return the entity written from DTO
     */
    public RecordingStartProperties writeRecordingStartProperties(RecordingStartRequestDto dto) {
        RecordingStartProperties entity = new RecordingStartProperties();
        entity.setRecordingType(
                convertRecordingTypeStringToEnum(dto.getRecordingType()));
        entity.setFileName(dto.getFileName());

        return entity;
    }

    /**
     * Write the response DTO from the provided entity.
     * </p>
     * The response DTO can be serialized to JSON and sent to client.
     *
     * @param entity the entity which represents the started recording
     * @return the response DTO which represents the started recording
     */
    public RecordingStartResponseDto writeRecordingStartResponseDto(StartedRecording entity) {
        RecordingStartResponseDto dto = new RecordingStartResponseDto();
        dto.setId(entity.getId());
        dto.setSessionId(entity.getSessionId());
        dto.setRecordingStatus(
                convertRecordingStatusEnumToString(entity.getStatus()));
        dto.setRecordingType(
                convertRecordingTypeEnumToString(entity.getType()));
        dto.setFileName(entity.getFileName());
        dto.setHasAudio(entity.hasAudio());
        dto.setHasVideo(entity.hasVideo());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    /**
     * Write the response DTO from the provided entity.
     * </p>
     * The response DTO can be serialized to JSON and sent to client.
     *
     * @param entity the entity which represents the stopped recording
     * @return the response DTO which represents the stopped recording
     */
    public RecordingStopResponseDto writeRecordingStopResponseDto(StoppedRecording entity) {
        RecordingStopResponseDto dto = new RecordingStopResponseDto();
        dto.setId(entity.getId());
        dto.setSessionId(entity.getSessionId());
        dto.setRecordingStatus(
                convertRecordingStatusEnumToString(entity.getStatus()));
        dto.setRecordingType(
                convertRecordingTypeEnumToString(entity.getType()));
        dto.setFileName(entity.getFileName());
        dto.setFileUrl(entity.getFileUrl());
        dto.setFileSize(entity.getFileSize());
        dto.setDuration(entity.getDuration());
        dto.setHasAudio(entity.hasAudio());
        dto.setHasVideo(entity.hasVideo());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    private Recording.Type convertRecordingTypeStringToEnum(String type) {
        switch (type) {
            case "AUDIO":
                return Recording.Type.AUDIO;
            case "VIDEO":
                return Recording.Type.VIDEO;
            case "SCREEN_SHARING":
                return Recording.Type.SCREEN_SHARING;
            default:
                throw new IllegalArgumentException(
                        "Unable to convert the provided recording type. Consider to extend the method.");
        }
    }

    private String convertRecordingTypeEnumToString(Recording.Type type) {
        if (type == null) {
            return "NOT_PROVIDED";
        }

        switch (type) {
            case AUDIO:
                return "AUDIO";
            case VIDEO:
                return "VIDEO";
            case SCREEN_SHARING:
                return "SCREEN_SHARING";
            default:
                throw new IllegalArgumentException(
                        "Unable to convert the provided recording type. Consider to extend the method.");
        }
    }

    private String convertRecordingStatusEnumToString(Recording.Status status) {
        if (status == null) {
            return "NOT_PROVIDED";
        }

        switch (status) {
            case STARTING:
                return "STARTING";
            case STARTED:
                return "STARTED";
            case STOPPED:
                return "STOPPED";
            case AVAILABLE:
                return "AVAILABLE";
            case FAILED:
                return "FAILED";
            default:
                throw new IllegalArgumentException(
                        "Unable to convert the provided recording status. Consider to extend the method.");
        }
    }
}
