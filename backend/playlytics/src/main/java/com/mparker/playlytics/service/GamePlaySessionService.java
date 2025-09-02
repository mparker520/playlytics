package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GamePlaySessionDTO;
import com.mparker.playlytics.dto.GamePlaySessionResponseDTO;
import com.mparker.playlytics.dto.SessionParticipantDTO;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
    private List<SessionParticipant> createSessionParticipantList(List<SessionParticipantDTO> sessionParticipantsDTOList) {
        List<SessionParticipant> sessionParticipantsList = new ArrayList<>();

        for (SessionParticipantDTO sessionParticipantDTO : sessionParticipantsDTOList) {
            int result = sessionParticipantDTO.result();
            Long playerId = sessionParticipantDTO.playerId();
            Player player = playerRepository.getReferenceById(playerId);

            SessionParticipant sessionParticipant = new SessionParticipant(result, player);
            sessionParticipantsList.add(sessionParticipant);
        }
    }

    // Attach GamePlaySession and SessionParticipants Helper Method
    private void addGpSessionParticipants(GamePlaySession gamePlaySession, List<SessionParticipant> sessionParticipantsList) {

        for (SessionParticipant sessionParticipant : sessionParticipantsList) {
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

    // Create GamePlaySessionResponseDTO
    private GamePlaySessionResponseDTO createGpSessionResponseDTO(GamePlaySession gamePlaySession) {
        Instant sessionDateTime = gamePlaySession.getSessionDateTime();
        ScoringModel scoringModel = gamePlaySession.getScoringModel();
        String gameName = gamePlaySession.getGame().getGameTitle();
        Set<SessionParticipant> sessionParticipants = gamePlaySession.getSessionParticipants();

        return new GamePlaySessionResponseDTO(sessionDateTime, scoringModel, gameName, sessionParticipants);

    }


    // Create GamePlaySession -- No Teams
    @Transactional
    public GamePlaySessionResponseDTO assembleGpSession(GamePlaySessionDTO gamePlaySessionDTO, List<SessionParticipantDTO> sessionParticipantsDTOList) {

        // Create GamePlaySession
        GamePlaySession gamePlaySession = createGpSession(gamePlaySessionDTO);

        // Create SessionParticipants from SessionParticipantDTO List
        List<SessionParticipant> sessionParticipantsList = createSessionParticipantList(sessionParticipantsDTOList);

        // Link GamePlaySession and SessionParticipants together
        addGpSessionParticipants(gamePlaySession, sessionParticipantsList);

        // Save GamePlaySession
        gamePlaySessionRepository.save(gamePlaySession);

        // Create and Return GamePlaySessionResponse DTO

        return createGpSessionResponseDTO(gamePlaySession);

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
