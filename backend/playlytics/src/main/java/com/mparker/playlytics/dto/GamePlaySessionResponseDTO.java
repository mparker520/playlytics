package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.entity.SessionParticipant;
import com.mparker.playlytics.entity.SessionTeam;
import com.mparker.playlytics.enums.ScoringModel;
import java.time.Instant;
import java.util.Set;

public record GamePlaySessionResponseDTO(Instant sessionDateTime, ScoringModel scoringModel, String gameName, Set<Long> sessionParticipantIds, Set<Long> sessionTeamIds) {
}
