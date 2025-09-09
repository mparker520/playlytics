package com.mparker.playlytics.dto;

// Imports

public record GhostPlayerDTO(String firstName, String lastName, byte[] avatar, String identifierEmail, Long registeredPlayerId, Long creatorId) {
}
