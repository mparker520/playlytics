package com.mparker.playlytics.dto;

public record RegisteredPlayerResponseDTO(String firstName, String lastName, byte[] avatar, String loginEmail, String displayName) {
}
