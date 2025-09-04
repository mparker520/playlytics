package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.GhostStatus;

public record GhostPlayerUpdateDTO(String firstName, String lastName, byte[] avatar, String identifierEmail, GhostStatus status, Long registeredPlayerId) {
}
