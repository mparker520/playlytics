package com.mparker.playlytics.services;

// Imports
import com.mparker.playlytics.entities.GamePlaySession;
import com.mparker.playlytics.entities.SessionParticipant;
import com.mparker.playlytics.repositories.GamePlaySessionRepository;
import com.mparker.playlytics.repositories.SessionParticipantRepository;
import com.mparker.playlytics.repositories.SessionTeamRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class GamePlaySessionService {

    //<editor-fold desc = "Constructors and Dependencies">

    public final GamePlaySessionRepository gamePlaySessionRepository;
    public final SessionParticipantRepository sessionParticipantRepository;
    public final SessionTeamRepository sessionTeamRepository;

    public GamePlaySessionService(final GamePlaySessionRepository gamePlaySessionRepository, final SessionParticipantRepository sessionParticipantRepository, final SessionTeamRepository sessionTeamRepository) {
        this.gamePlaySessionRepository = gamePlaySessionRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
        this.sessionTeamRepository = sessionTeamRepository;
    }


    //</editor-fold>


    // <editor-fold desc = "Create GamePlaySession">
    @Transactional
    public GamePlaySession createGamePlaySession(GamePlaySession gamePlaySession, List<SessionParticipant> sessionParticipants) {

        for (SessionParticipant sessionParticipant : sessionParticipants) {
                sessionParticipant.setGamePlaySession(gamePlaySession);
                gamePlaySession.getSessionParticipants().add(sessionParticipant);
        }

        return gamePlaySessionRepository.save(gamePlaySession);

    }


    //</editor-fold>


    // <editor-fold desc = "Update GamePlaySession">
    //</editor-fold>


    // <editor-fold desc = "Lookup GamePlaySession">


    //</editor-fold>


    // <editor-fold desc = "Delete GamePlaySession">


    // Delete GamePlaySession by passing in GamePlaySession through Internal Validation
    @Transactional
    public void deleteGamePlaySession(GamePlaySession gamePlaySession) {

        if (gamePlaySession != null) {
            gamePlaySessionRepository.delete(gamePlaySession);
        }

    }


    // Delete Game by Checking that Current User was a SessionParticipant in the GamePlaySession
    @Transactional
    public void deleteGamePlaySessionByIdAndPlayerId(Long id, Long playerId) {

        if(id != null && playerId != null) {

            boolean playerIsParticipant = sessionParticipantRepository.existsByGamePlaySession_IdAndPlayer_Id(id, playerId);

            if(playerIsParticipant == true) {
                gamePlaySessionRepository.deleteById(id);
            }

        }

    }


    //</editor-fold>

}
