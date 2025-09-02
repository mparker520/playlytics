package com.mparker.playlytics.dto;

// Imports

import com.mparker.playlytics.enums.GhostStatus;

public record GhostPlayerResponseDTO(String firstName, String lastName, byte[] avatar, String identifierEmail, Long creatorId) {
}
