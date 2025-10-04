package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.ConnectionRequestStatus;

public record ConnectionRequestResponseDTO(Long id, Long senderId, String senderFirstName, String senderLastName, String senderEmail, Long recipientId, String recipientFirstName, String recipientLastName, String recipientEmail, ConnectionRequestStatus connectionRequestStatus) {
}
