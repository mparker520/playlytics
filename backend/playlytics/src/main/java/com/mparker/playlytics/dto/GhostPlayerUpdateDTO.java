package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.GhostStatus;
import org.openapitools.jackson.nullable.JsonNullable;

public record GhostPlayerUpdateDTO(JsonNullable<String> firstName, JsonNullable<String> lastName, JsonNullable<byte[]> avatar, JsonNullable<String> identifierEmail, JsonNullable<GhostStatus> status, JsonNullable<Long> registeredPlayerId) {
}
