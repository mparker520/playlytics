package com.mparker.playlytics.dto;

public record BlockedRelationshipResponseDTO(Long blockerId, Long blockedId, boolean blocked) {
}
