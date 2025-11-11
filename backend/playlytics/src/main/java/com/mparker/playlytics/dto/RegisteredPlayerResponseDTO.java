package com.mparker.playlytics.dto;

public record RegisteredPlayerResponseDTO(Long id, String firstName, String lastName, byte[] avatar, String loginEmail, String displayName) {
}
