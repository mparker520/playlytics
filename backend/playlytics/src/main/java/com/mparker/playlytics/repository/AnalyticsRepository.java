package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.dto.analytics.OwnedGameFrequencyProjection;
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.dto.analytics.PlayTrendProjection;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.enums.ScoringModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


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

    //<editor-fold desc="Get Owned Game Frequency by Name">
    @Query (value = """
            SELECT bg.game_title AS title, COALESCE(COUNT(gps.game_id), 0) AS playCount
					FROM owned_games AS og
					JOIN board_games AS bg ON og.game_id = bg.id
					LEFT JOIN game_play_sessions AS gps ON gps.game_id = bg.id
					LEFT JOIN session_participants AS sp 
										    ON gps.id = sp.game_play_session_id
										    AND sp.player_id = :playerId
					WHERE og.owner_id = :playerId
										AND og.game_id = :gameId
					GROUP BY bg.game_title
					ORDER BY playCount DESC

            """,
            nativeQuery = true
    )

    OwnedGameFrequencyProjection getOwnedGameFrequencyByName(@Param ("playerId") Long authUserId, @Param("gameId") Long gameId);
    //</editor-fold>

    //<editor-fold desc="Get Top 10 Owned Game Frequency">
    @Query (value = """
            SELECT sub.title AS title, sub.playCount AS playCount
            FROM (
                    SELECT bg.game_title AS title, 
                                COALESCE(COUNT(gps.game_id), 0) AS playCount,
                                DENSE_RANK() OVER (ORDER BY COUNT(gps.id) DESC) AS rank
                            FROM owned_games AS og
                            JOIN board_games AS bg ON og.game_id = bg.id
                            LEFT JOIN game_play_sessions AS gps ON gps.game_id = bg.id
                            LEFT JOIN session_participants AS sp 
                                                    ON gps.id = sp.game_play_session_id
                                                    AND sp.player_id = :playerId
                            WHERE og.owner_id = :playerId
                            GROUP BY bg.game_title
                                        ) sub
                                WHERE sub.rank <= 5
                                ORDER BY sub.playCount DESC
            """,
            nativeQuery = true
    )

    List<OwnedGameFrequencyProjection> getOwnedGameFrequencyTopFive(@Param ("playerId") Long authUserId);
    //</editor-fold>

    //<editor-fold desc="Get Bottom 10 Owned Game Frequency">
    @Query (value = """
            SELECT sub.title AS title, sub.playCount AS playCount
            FROM (
                    SELECT bg.game_title AS title, 
                                COALESCE(COUNT(gps.game_id), 0) AS playCount,
                                DENSE_RANK() OVER (ORDER BY COUNT(gps.id) ASC) AS rank
                            FROM owned_games AS og
                            JOIN board_games AS bg ON og.game_id = bg.id
                            LEFT JOIN game_play_sessions AS gps ON gps.game_id = bg.id
                            LEFT JOIN session_participants AS sp 
                                                    ON gps.id = sp.game_play_session_id
                                                    AND sp.player_id = :playerId
                            WHERE og.owner_id = :playerId
                            GROUP BY bg.game_title
                                        ) sub
                                WHERE sub.rank <= 6
                                ORDER BY sub.playCount DESC
            """,
            nativeQuery = true
    )

    List<OwnedGameFrequencyProjection> getOwnedGameFrequencyBottomFive(@Param ("playerId") Long authUserId);

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Play Trends">


    //<editor-fold desc="Play Trends By Game Granularity Month">

    @Query (value = """

            SELECT EXTRACT(YEAR FROM gps.session_date_time) AS yearPlayed,  
                            EXTRACT(MONTH FROM gps.session_date_time) AS monthPlayed,
                            bg.game_title AS title, 
                            COUNT(gps.game_id) AS playCount
                        FROM session_participants AS sp
                        JOIN game_play_sessions AS gps ON sp.game_play_session_id = gps.id
                        JOIN board_games AS bg ON gps.game_id = bg.id
                            WHERE sp.player_id = :playerId
                            AND ((:game1Id IS NULL AND :game2Id IS NULL) 
                                                  OR (bg.id = :game1Id OR bg.id = :game2Id) OR (bg.id = :game1Id AND :game2Id IS NULL)) 
                            AND EXTRACT(YEAR FROM gps.session_date_time) BETWEEN :startingYear   AND :endYear     
                        GROUP BY EXTRACT(YEAR FROM gps.session_date_time), 
                                             EXTRACT(MONTH FROM gps.session_date_time),
                                             bg.game_title 
                        ORDER BY yearPlayed ASC, 
                                             monthPlayed ASC,
                                             bg.game_title ASC	
                                
            """,
            nativeQuery = true
    )

    List<PlayTrendProjection> getPlayTrendsByGameGranularityMonth(
            @Param ("playerId") Long authUserId,
            @Param("game1Id") Long selectedGame1Id, @Param("game2Id") Long selectedGame2Id,
            @Param("startingYear") Long selectedStartingYear, @Param("endYear") Long selectedEndingYear);
    //</editor-fold>


    //<editor-fold desc="Play Trends By Game Granularity Year">

    @Query (value = """

            SELECT EXTRACT(YEAR FROM gps.session_date_time) AS yearPlayed, 
                            bg.game_title AS title, 
                            COUNT(gps.game_id) AS playCount
                        FROM session_participants AS sp
                        JOIN game_play_sessions AS gps ON sp.game_play_session_id = gps.id
                        JOIN board_games AS bg ON gps.game_id = bg.id
                            WHERE sp.player_id = :playerId
                            AND ((:game1Id IS NULL AND :game2Id IS NULL) 
                                                 OR (bg.id = :game1Id OR bg.id = :game2Id) OR (bg.id = :game1Id AND :game2Id IS NULL)) 
                            AND EXTRACT(YEAR FROM gps.session_date_time) BETWEEN :startingYear   AND :endYear     
                        GROUP BY EXTRACT(YEAR FROM gps.session_date_time), 
                                             bg.game_title 
                        ORDER BY yearPlayed ASC, 
                                             bg.game_title ASC	
                                
            """,
            nativeQuery = true
    )

    List<PlayTrendProjection> getPlayTrendsByGameGranularityYear(
            @Param ("playerId") Long authUserId,
            @Param("game1Id") Long selectedGame1Id, @Param("game2Id") Long selectedGame2Id,
            @Param("startingYear") Long selectedStartingYear, @Param("endYear") Long selectedEndingYear);
    //</editor-fold>


    //</editor-fold>

}
