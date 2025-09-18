package com.mparker.playlytics.dto;

public record RegisteredPlayerDTO(String loginEmail, String password, String firstName, String lastName,  String displayName, byte[] avatar) {
}
