package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.entity.GamePlaySession;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Repository
public interface GamePlaySessionRepository extends JpaRepository<GamePlaySession, Long> {


    //<editor-fold desc="Get all game play sessions by creator">
        Set<GamePlaySession> findAllByCreator_Id(Long registeredPlayerId);
    //</editor-fold>

    //<editor-fold desc="Select Titles of all games played by user">

    @Query (("""

    SELECT DISTINCT new com.mparker.playlytics.dto.GameResponseDTO(g.id, g.gameTitle)
    FROM SessionParticipant sp
    JOIN sp.gamePlaySession gsp
    JOIN gsp.game g
    WHERE sp.player.id = :playerId
    ORDER BY g.gameTitle ASC

                                                                """))

    List<GameResponseDTO> getAllPlayedGames(@Param ("playerId") Long authUserId);


    //</editor-fold>

    // TODO: Check if this is used
    List<GamePlaySession> game(@NotNull Game game);


    //<editor-fold desc="Find all game play sessions by player id and paramaters">

    @Query (value = """

            SELECT gps.*
                        FROM session_participants sp
                        JOIN game_play_sessions AS gps ON sp.game_play_session_id = gps.id
                            WHERE sp.player_id = :playerId
                            AND (:gameId IS NULL OR gps.game_id = :gameId)
                            AND gps.session_date_time BETWEEN :startDate AND :endDate
                            ORDER BY gps.session_date_time ASC""",
            nativeQuery = true
    )
    List<GamePlaySession> findAllPlayerIdAndParams(@Param("playerId") Long authenticatedUserId, @Param("gameId") Long gameId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    //</editor-fold>


}
