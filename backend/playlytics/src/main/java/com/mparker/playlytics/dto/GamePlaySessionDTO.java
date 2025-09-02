package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.ScoringModel;
import java.time.Instant;

public record GamePlaySessionDTO(Instant sessionDateTime, ScoringModel scoringModel, Long creatorId, Long gameId) {
}
