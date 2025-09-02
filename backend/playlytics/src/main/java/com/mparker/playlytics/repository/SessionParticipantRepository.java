package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {

    // Is Player a participant in GamePlaySession
    public boolean existsByGamePlaySession_IdAndPlayer_Id(Long gamePlaySessionId, Long playerId);

    public SessionParticipant existsByPlayer_Id(Long playerId);

}
