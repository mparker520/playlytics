package com.mparker.playlytics.dto;

// Imports

import com.mparker.playlytics.enums.ConnectionRequestStatus;

import java.time.Instant;

public record ConfirmedConnectionResponseDTO(Long peerAId, Long peerBId, ConnectionRequestStatus connectionRequestStatus) {
}
