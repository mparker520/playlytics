package com.mparker.playlytics.dto;

public record AuthResponseDTO(boolean success, Long userId, String email, String firstName, String lastName, String displayName) {
}
