package com.mparker.playlytics.dto;


public record GhostPlayerResponseDTO(Long id, String firstName, String lastName, byte[] avatar, String identifierEmail, Long creatorId) {
}
