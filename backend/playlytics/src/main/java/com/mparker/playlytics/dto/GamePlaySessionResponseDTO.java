package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.ScoringModel;
import java.time.Instant;
import java.util.Set;

public record GamePlaySessionResponseDTO(Long id, Instant sessionDateTime, ScoringModel scoringModel, String gameName, Set<Long> sessionParticipantIds, Set<Long> sessionTeamIds) {
}
