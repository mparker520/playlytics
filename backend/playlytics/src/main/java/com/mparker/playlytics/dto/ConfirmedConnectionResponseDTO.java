package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.ConnectionRequestStatus;


public record ConfirmedConnectionResponseDTO(Long peerAId, Long peerBId, ConnectionRequestStatus connectionRequestStatus) {
}
