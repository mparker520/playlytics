package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.GhostStatus;

public record GhostPlayerDTO(String firstName, String lastName, byte[] avatar, String identifierEmail, GhostStatus status, Long registeredPlayerId, Long creatorId) {
}
