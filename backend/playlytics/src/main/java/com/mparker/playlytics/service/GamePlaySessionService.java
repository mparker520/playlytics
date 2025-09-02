package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GamePlaySessionDTO;
import com.mparker.playlytics.dto.GamePlaySessionResponseDTO;
import com.mparker.playlytics.dto.SessionParticipantDTO;
import com.mparker.playlytics.dto.SessionTeamDTO;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Service

public class GamePlaySessionService {

    //<editor-fold desc = "Constructors and Dependencies">

    public final GamePlaySessionRepository gamePlaySessionRepository;
    public final SessionParticipantRepository sessionParticipantRepository;
    public final SessionTeamRepository sessionTeamRepository;
    public final PlayerRepository playerRepository;
    public final GameRepository gameRepository;

    public GamePlaySessionService(final GamePlaySessionRepository gamePlaySessionRepository, final SessionParticipantRepository sessionParticipantRepository, final SessionTeamRepository sessionTeamRepository, final PlayerRepository playerRepository, final GameRepository gameRepository) {
        this.gamePlaySessionRepository = gamePlaySessionRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
        this.sessionTeamRepository = sessionTeamRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }


    //</editor-fold>

    // <editor-fold desc = "Create GamePlaySession">

    // Assemble GamePlaySession
    @Transactional
    public GamePlaySessionResponseDTO assembleGpSession(GamePlaySessionDTO gamePlaySessionDTO, Set<SessionParticipantDTO> sessionParticipantsDTOSet, Set<SessionTeamDTO> sessionTeamsDTOSet) {

        // Create GamePlaySession
        GamePlaySession gamePlaySession = createGpSession(gamePlaySessionDTO);

        // Create SessionParticipants from sessionParticipantDTO Set
        Set <SessionParticipant> sessionParticipantSet = createSessionParticipantsSet(sessionParticipantsDTOSet);

        // Create SessionTeams from sessionTeamsDTOList If Scoring Model TEAMS and sessionTeamsDTOList not Empty
        if (gamePlaySession.getScoringModel() == ScoringModel.TEAM && sessionTeamsDTOSet.size() > 1) {

            // Create Session Teams
            Set<SessionTeam> sessionTeamSet = createSessionTeamSet(sessionTeamsDTOSet, sessionParticipantSet);

            // Link Teams and GamePlaySession
            linkTeamsAndGpSession(gamePlaySession, sessionTeamSet);

        }

        // Link GamePlaySession and SessionParticipants together
        linkGpSessionAndParticipants(gamePlaySession, sessionParticipantSet);

        // Save GamePlaySession
        gamePlaySessionRepository.save(gamePlaySession);

        // Create and Return GamePlaySessionResponse DTO
        return createGpSessionResponseDTO(gamePlaySession);

    }


    //</editor-fold>

    // <editor-fold desc = "Helper Methods for Assembling a GamePlaySession">

    // Create GamePlaySession Helper Method
    private GamePlaySession createGpSession(GamePlaySessionDTO gamePlaySessionDTO) {

        Instant sessionDateTime = gamePlaySessionDTO.sessionDateTime();
        ScoringModel scoringModel = gamePlaySessionDTO.scoringModel();
        Long creatorId = gamePlaySessionDTO.creatorId();
        Long gameId = gamePlaySessionDTO.gameId();
        Player creator = playerRepository.getReferenceById(creatorId);
        Game game = gameRepository.getReferenceById(gameId);
        return new GamePlaySession(sessionDateTime, scoringModel, creator, game);

    }

    // Create SessionParticipants Helper Method
    private Set<SessionParticipant> createSessionParticipantsSet(Set<SessionParticipantDTO> sessionParticipantsDTOSet) {
        Set<SessionParticipant> sessionParticipantsSet = new HashSet<>();

        for (SessionParticipantDTO sessionParticipantDTO : sessionParticipantsDTOSet) {
            int result = sessionParticipantDTO.result();
            Long playerId = sessionParticipantDTO.playerId();
            Player player = playerRepository.getReferenceById(playerId);

            SessionParticipant sessionParticipant = new SessionParticipant(result, player);
            sessionParticipantsSet.add(sessionParticipant);
        }

        return sessionParticipantsSet;

    }

    // Create SessionTeams Helper Method
    private Set<SessionTeam> createSessionTeamSet(Set<SessionTeamDTO> sessionTeamDTOSet, Set<SessionParticipant> sessionParticipantsSet) {

        Set<SessionTeam> sessionTeamsSet = new HashSet<>();

        for (SessionTeamDTO sessionTeamDTO : sessionTeamDTOSet) {

            int result = sessionTeamDTO.result();
            String teamName = sessionTeamDTO.teamName();

            // Constructor for SessionTeam
            SessionTeam sessionTeam = new SessionTeam(result, teamName);

            for (Long playerId : sessionTeamDTO.playerIds()) {

                for (SessionParticipant sessionParticipant : sessionParticipantsSet) {
                    if (sessionParticipant.getId().equals(playerId)) {
                        sessionParticipant.setSessionTeam(sessionTeam);
                        sessionTeam.getTeamMembers().add(sessionParticipant);
                    }
                }

            }

            sessionTeamsSet.add(sessionTeam);

        }

        return sessionTeamsSet;

    }

    // Attach GamePlaySession and SessionParticipants Helper Method
    private void linkGpSessionAndParticipants(GamePlaySession gamePlaySession, Set<SessionParticipant> sessionParticipantsSet) {

        for (SessionParticipant sessionParticipant : sessionParticipantsSet) {
            sessionParticipant.setGamePlaySession(gamePlaySession);
            gamePlaySession.getSessionParticipants().add(sessionParticipant);
        }

    }

    // Attach GamePlaySession and SessionTeams Helper Method
    private void linkTeamsAndGpSession(GamePlaySession gamePlaySession, Set<SessionTeam> sessionTeamSet) {
        for (SessionTeam sessionTeam : sessionTeamSet) {
            sessionTeam.setGamePlaySession(gamePlaySession);
            gamePlaySession.getSessionTeams().add(sessionTeam);
        }
    }

    // Create GamePlaySessionResponseDTO
    private GamePlaySessionResponseDTO createGpSessionResponseDTO(GamePlaySession gamePlaySession) {
        Instant sessionDateTime = gamePlaySession.getSessionDateTime();
        ScoringModel scoringModel = gamePlaySession.getScoringModel();
        String gameName = gamePlaySession.getGame().getGameTitle();
        Set<SessionParticipant> sessionParticipants = gamePlaySession.getSessionParticipants();

        return new GamePlaySessionResponseDTO(sessionDateTime, scoringModel, gameName, sessionParticipants);

    }

    // </editor-fold>

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
