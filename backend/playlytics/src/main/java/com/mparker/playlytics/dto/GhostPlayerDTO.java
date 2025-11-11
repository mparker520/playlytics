package com.mparker.playlytics.dto;


public record GhostPlayerDTO(String firstName, String lastName, byte[] avatar, String identifierEmail, Long linkedRegisteredPlayerId, Long creatorId) {
}
