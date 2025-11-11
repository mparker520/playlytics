package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {

    // Is Player a participant in GamePlaySession
    boolean existsByGamePlaySession_IdAndPlayer_Id(Long gamePlaySessionId, Long playerId);

    // Get all Session Participants by Player ID
    Set<SessionParticipant> findAllByPlayer_Id(Long playerId);

}
