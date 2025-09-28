package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.enums.ScoringModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface AnalyticsRepository extends JpaRepository<GhostPlayer, Long> {


    @Query (("""
            SELECT 
                COALESCE(SUM(CASE WHEN sp.result = 1 THEN 1 ELSE 0 END), 0) AS wins,
                COALESCE(SUM(CASE WHEN sp.result <> 1 THEN 1 ELSE 0 END), 0) AS losses
            FROM SessionParticipant sp
            WHERE sp.player.id = :playerId
            AND (:gameId IS NULL OR sp.gamePlaySession.game.id = :gameId)
            AND (:scoringModel IS NULL OR sp.gamePlaySession.scoringModel = :scoringModel)
            
            """))
    WinLossProjection getWinLossRatio(@Param ("playerId") Long authUserId, @Param("gameId") Long gameId, @Param("scoringModel") ScoringModel scoringModel);

}
