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

    //<editor-fold desc="Owned Game Play Frequency">

    //<editor-fold desc="Get All Owned Game Frequency">
    @Query (value = """
            SELECT bg.game_title AS title, COALESCE(COUNT(gps.game_id), 0) AS playCount
					FROM owned_games AS og
					JOIN board_games AS bg ON og.game_id = bg.id
					LEFT JOIN game_play_sessions AS gps ON gps.game_id = bg.id
					LEFT JOIN session_participants AS sp 
										    ON gps.id = sp.game_play_session_id
										    AND sp.player_id = :playerId
					WHERE og.owner_id = :playerId
					GROUP BY bg.game_title
					ORDER BY playCount DESC

            """,
            nativeQuery = true
    )

    List<OwnedGameFrequencyProjection> getOwnedGameFrequencyAll(@Param ("playerId") Long authUserId);
    //</editor-fold>

    //<editor-fold desc="Get Top 10 Owned Game Frequency">
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
                    LIMIT 10
            """,
            nativeQuery = true
    )

    List<OwnedGameFrequencyProjection> getOwnedGameFrequencyTopTen(@Param ("playerId") Long authUserId);
    //</editor-fold>

    //<editor-fold desc="Get Bottom 10 Owned Game Frequency">
    @Query (value = """
                    SELECT *
                    FROM (
                        SELECT bg.game_title AS title, COUNT(gsp.game_id) AS playCount
                      FROM  session_participants AS sp
                    JOIN game_play_sessions AS gsp ON sp.game_play_session_id = gsp.id
                    JOIN board_games AS bg ON bg.id = gsp.game_id
                    JOIN owned_games AS og ON og.game_id = bg.id
                    WHERE sp.player_id = :playerId
                            AND  og.owner_id = :playerId
                    GROUP BY bg.game_title
                    ORDER BY playCount ASC
                    LIMIT 10 )
                    sub 
                    ORDER BY playCount DESC
            """,
            nativeQuery = true
    )

    List<OwnedGameFrequencyProjection> getOwnedGameFrequencyBottomTen(@Param ("playerId") Long authUserId);
    //</editor-fold>
    //</editor-fold>

}
