package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.entity.GamePlaySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface GamePlaySessionRepository extends JpaRepository<GamePlaySession, Long> {

    Set<GamePlaySession> findAllByCreator_Id(Long registeredPlayerId);


    @Query (("""

    SELECT DISTINCT new com.mparker.playlytics.dto.GameResponseDTO(g.id, g.gameTitle)
    FROM SessionParticipant sp
    JOIN sp.gamePlaySession gsp
    JOIN gsp.game g
    WHERE sp.player.id = :playerId
    ORDER BY g.gameTitle ASC

"""))
    List<GameResponseDTO> getAllPlayedGames(@Param ("playerId") Long authUserId);

}
