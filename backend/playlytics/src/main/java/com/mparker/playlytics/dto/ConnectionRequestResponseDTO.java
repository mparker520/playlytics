package com.mparker.playlytics.dto;

// Imports

import com.mparker.playlytics.entity.ConnectionRequest;
import com.mparker.playlytics.enums.ConnectionRequestStatus;

public record ConnectionRequestResponseDTO(Long senderId, Long receiverId, ConnectionRequestStatus connectionRequestStatus) {
}
