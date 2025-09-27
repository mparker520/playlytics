package com.mparker.playlytics.dto;

// Imports
import com.mparker.playlytics.enums.ScoringModel;
import java.time.Instant;
import java.util.Set;

public record GamePlaySessionDTO(Instant sessionDateTime, ScoringModel scoringModel, Long gameId, Set<SessionParticipantDTO> sessionParticipantDTOSet) {
}

//Set<SessionTeamDTO> sessionTeamDTOSet