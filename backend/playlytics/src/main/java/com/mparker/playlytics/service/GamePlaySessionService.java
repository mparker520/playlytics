package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.entity.GamePlaySession;
import com.mparker.playlytics.entity.SessionParticipant;
import com.mparker.playlytics.entity.SessionTeam;
import com.mparker.playlytics.repository.GamePlaySessionRepository;
import com.mparker.playlytics.repository.SessionParticipantRepository;
import com.mparker.playlytics.repository.SessionTeamRepository;
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


    // Attach GamePlaySession and SessionParticipants Helper Method
    private void addGpSessionParticipants(GamePlaySession gamePlaySession, List<SessionParticipant> sessionParticipants) {

        for (SessionParticipant sessionParticipant : sessionParticipants) {
            sessionParticipant.setGamePlaySession(gamePlaySession);
            gamePlaySession.getSessionParticipants().add(sessionParticipant);
        }

    }

    // Set Team for SessionParticipants Helper Method
    private void setTeamForParticipants(List<SessionTeam> sessionTeams) {

        for (SessionTeam sessionTeam : sessionTeams) {

            for(SessionParticipant sessionParticipant : sessionTeam.getTeamMembers()) {
                sessionParticipant.setSessionTeam(sessionTeam);
            }

        }

    }

    // Attach GamePlaySession and SessionTeams Helper Method
    private void addTeamsToGpSession(GamePlaySession gamePlaySession, List<SessionTeam> sessionTeams) {
        for (SessionTeam sessionTeam : sessionTeams) {
            sessionTeam.setGamePlaySession(gamePlaySession);
            gamePlaySession.getSessionTeams().add(sessionTeam);
        }
    }


    // Create GamePlaySession -- No Teams
    @Transactional
    public GamePlaySession createGpSession(GamePlaySession gamePlaySession, List<SessionParticipant> sessionParticipants) {

        addGpSessionParticipants(gamePlaySession, sessionParticipants);
        return gamePlaySessionRepository.save(gamePlaySession);

    }

    // Create GamePlaySession -- With Teams
    @Transactional
    public GamePlaySession createGpSessionWithTeams(GamePlaySession gamePlaySession, List<SessionParticipant> sessionParticipants, List<SessionTeam> sessionTeams) {

        setTeamForParticipants(sessionTeams);
        addTeamsToGpSession(gamePlaySession, sessionTeams);
        addGpSessionParticipants(gamePlaySession, sessionParticipants);
        return gamePlaySessionRepository.save(gamePlaySession);

    }


    //</editor-fold>

    // Update GamePlaySession will be Handled by Deleting and Recreating the GamePlaySession

    // <editor-fold desc = "Lookup GamePlaySession">



    //</editor-fold>

    // <editor-fold desc = "Delete GamePlaySession">

    // Delete Game by Checking that Current User was a SessionParticipant in the GamePlaySession
    @Transactional
    public void deleteGpSessionByIdAndPlayerId(Long id, Long playerId) {

            boolean playerIsParticipant = sessionParticipantRepository.existsByGamePlaySession_IdAndPlayer_Id(id, playerId);

            if(playerIsParticipant) {
                gamePlaySessionRepository.deleteById(id);
            }

    }


    //</editor-fold>

}
