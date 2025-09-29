package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.dto.analytics.OwnedGameFrequencyProjection;
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.enums.ScoringModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface AnalyticsRepository extends JpaRepository<GhostPlayer, Long> {


    //<editor-fold desc="Get Win Loss Ratio">
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
    //</editor-fold>



    //<editor-fold desc="Get Owned Game Frequency">
    @Query (value = """
            SELECT bg.game_title AS title, COUNT(gsp.game_id) AS playCount
                    FROM session_participants AS sp
                    JOIN game_play_sessions AS gsp ON sp.game_play_session_id = gsp.id
                    JOIN board_games AS bg ON bg.id = gsp.game_id
                    JOIN owned_games AS og ON og.game_id = bg.id
                    WHERE sp.player_id = :playerId
                            AND  og.owner_id = :playerId
                    GROUP BY bg.game_title
                    ORDER BY playCount DESC
            """,
            nativeQuery = true
    )

    List<OwnedGameFrequencyProjection> getOwnedGameFrequency(@Param ("playerId") Long authUserId);
    //</editor-fold>

}
