package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.entity.GamePlaySession;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface GamePlaySessionRepository extends JpaRepository<GamePlaySession, Long> {

    Set<GamePlaySession> findAllByCreator_Id(Long registeredPlayerId);


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

    List<GamePlaySession> game(@NotNull Game game);



    @Query (value = """

            SELECT gps.*
                        FROM session_participants sp
                        JOIN game_play_sessions AS gps ON sp.game_play_session_id = gps.id
                            WHERE sp.player_id = :playerId
                            AND gps.game_id = :gameId
                            AND gps.session_date_time BETWEEN :startDate AND :endDate
                            ORDER BY gps.session_date_time ASC""",
            nativeQuery = true
    )
    List<GamePlaySession> findAllPlayerIdAndParams(@Param("playerId") Long authenticatedUserId, @Param("gameId") Long gameId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
