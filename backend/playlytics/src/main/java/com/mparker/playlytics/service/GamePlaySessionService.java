package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GamePlaySessionDTO;
import com.mparker.playlytics.dto.GamePlaySessionResponseDTO;
import com.mparker.playlytics.dto.SessionParticipantDTO;
import com.mparker.playlytics.dto.SessionTeamDTO;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.exception.CustomAccessDeniedException;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.exception.SessionParticipantTeamMismatchException;
import com.mparker.playlytics.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Service

public class GamePlaySessionService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final GamePlaySessionRepository gamePlaySessionRepository;
    private final SessionParticipantRepository sessionParticipantRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final ConfirmedConnectionRepository confirmedConnectionRepository;
    private final GhostPlayerRepository ghostPlayerRepository;

    public GamePlaySessionService(final GamePlaySessionRepository gamePlaySessionRepository, final SessionParticipantRepository sessionParticipantRepository, final PlayerRepository playerRepository, final GameRepository gameRepository, RegisteredPlayerRepository registeredPlayerRepository, ConfirmedConnectionRepository confirmedConnectionRepository, GhostPlayerRepository ghostPlayerRepository) {
        this.gamePlaySessionRepository = gamePlaySessionRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.confirmedConnectionRepository = confirmedConnectionRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
    }


    //</editor-fold>

    // <editor-fold desc = "Create GamePlaySession">

    // Assemble GamePlaySession
    @Transactional
    public GamePlaySessionResponseDTO assembleGpSession(GamePlaySessionDTO gamePlaySessionDTO, Long authUserId) {

        // Create GamePlaySession
        GamePlaySession gamePlaySession = createGpSession(gamePlaySessionDTO, authUserId);

        // Create SessionParticipants from sessionParticipantDTO Set
        Set <SessionParticipant> sessionParticipantSet = createSessionParticipantsSet(gamePlaySessionDTO.sessionParticipantDTOSet(), authUserId);

        // Create SessionTeams from sessionTeamsDTOList If Scoring Model TEAMS and sessionTeamsDTOList not Empty
        if (gamePlaySession.getScoringModel() == ScoringModel.TEAM && gamePlaySessionDTO.sessionTeamDTOSet().size() > 1) {

            // Create Session Teams
            Set<SessionTeam> sessionTeamSet = createSessionTeamSet(gamePlaySessionDTO.sessionTeamDTOSet(), sessionParticipantSet);

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

    // Update GamePlaySession will be Handled by Deleting and Recreating the GamePlaySession

    // <editor-fold desc = "Lookup GamePlaySession">


    // List of all GamePlaySessions for a RegisteredPlayer
    @Transactional (readOnly = true)
    public Set<GamePlaySessionResponseDTO> findAllByPlayerId(Long registeredPlayerId, Long authUserId) throws CustomAccessDeniedException{

        if(registeredPlayerId.equals(authUserId)) {

            Set<GamePlaySessionResponseDTO> gamePlaySessionResponseDTOSet = new HashSet<>();

            Set<SessionParticipant> associatedSessionParticipants = sessionParticipantRepository.findAllByPlayer_Id(registeredPlayerId);
            for (SessionParticipant sessionParticipant : associatedSessionParticipants) {
                GamePlaySession gamePlaySession = sessionParticipant.getGamePlaySession();
                gamePlaySessionResponseDTOSet.add(createGpSessionResponseDTO(gamePlaySession));
            }

            return gamePlaySessionResponseDTOSet;

        }

        else {
            throw new CustomAccessDeniedException("You do not have authorization to view Game Play Sessions in which you did not participate.");
        }

    }

    // List of all GamePlaySessions for a RegisteredPlayer by Game Title
    @Transactional (readOnly = true)
    public Set<GamePlaySessionResponseDTO> findAllByPlayerIdAndGameName(Long registeredPlayerId, String gameTitle, Long authUserId) throws CustomAccessDeniedException, NotFoundException{

        if (registeredPlayerId.equals(authUserId)) {

            Set<GamePlaySessionResponseDTO> gamePlaySessionResponseDTOSet = new HashSet<>();
            Set<SessionParticipant> associatedSessionParticipants = sessionParticipantRepository.findAllByPlayer_Id(registeredPlayerId);

            for (SessionParticipant sessionParticipant : associatedSessionParticipants) {
                GamePlaySession gamePlaySession = sessionParticipant.getGamePlaySession();
                String gamePlaySessionName = gamePlaySession.getGame().getGameTitle();
                if (gamePlaySessionName.equals(gameTitle)) {
                    gamePlaySessionResponseDTOSet.add(createGpSessionResponseDTO(gamePlaySession));
                }
            }

            if (!gamePlaySessionResponseDTOSet.isEmpty()) {
                return gamePlaySessionResponseDTOSet;
            }

            else {
                throw new NotFoundException("No Game Play Sessions found for given Game Title.");
            }

        }

        else {
            throw new CustomAccessDeniedException("You do not have authorization to view Game Play Sessions in which you did not participate.");
        }


    }

    // List of all GamePlaySessions for a RegisteredPlayer by Date
    // TODO: Fix time conversion
    @Transactional (readOnly = true)
    public Set<GamePlaySessionResponseDTO> getAllGpSessionsByPlayerIdAndDate(Long playerId, Instant sessionDateTime) {

        Set<GamePlaySessionResponseDTO> gamePlaySessionResponseDTOSet = new HashSet<>();

        Set<SessionParticipant> associatedSessionParticipants = sessionParticipantRepository.findAllByPlayer_Id(playerId);
        for (SessionParticipant sessionParticipant : associatedSessionParticipants) {
            GamePlaySession gamePlaySession = sessionParticipant.getGamePlaySession();
            Instant gamePlaySessionDateTime = gamePlaySession.getSessionDateTime();

            if (gamePlaySessionDateTime.equals(sessionDateTime)) {
                gamePlaySessionResponseDTOSet.add(createGpSessionResponseDTO(gamePlaySession));
            }
        }

        return gamePlaySessionResponseDTOSet;

    }



    //</editor-fold>

    // <editor-fold desc = "Delete GamePlaySession">

    // Delete Game by Checking that Current User was a SessionParticipant in the GamePlaySession
    @Transactional
    public void deleteByIdAndPlayerId(Long sessionId, Long authUserId) throws CustomAccessDeniedException {

            boolean authUserIsParticipant = sessionParticipantRepository.existsByGamePlaySession_IdAndPlayer_Id(sessionId, authUserId);

            if(authUserIsParticipant) {
                gamePlaySessionRepository.deleteById(sessionId);
            }

            else {
                throw new CustomAccessDeniedException("You do not have permission to delete this Game Play Session Because You were Not a Participant");
            }

    }


    //</editor-fold>

    // <editor-fold desc = "Helper Methods for Assembling a GamePlaySession">

    // Create GamePlaySession Helper Method
    private GamePlaySession createGpSession(GamePlaySessionDTO gamePlaySessionDTO, Long authUserId) throws CustomAccessDeniedException, NotFoundException {

        Long creatorId = gamePlaySessionDTO.creatorId();

        if(creatorId.equals(authUserId)) {

            Long gameId = gamePlaySessionDTO.gameId();

            if (gameRepository.existsById(gameId)) {

                Instant sessionDateTime = gamePlaySessionDTO.sessionDateTime();
                ScoringModel scoringModel = gamePlaySessionDTO.scoringModel();
                Player creator = playerRepository.getReferenceById(creatorId);
                Game game = gameRepository.getReferenceById(gameId);

                return new GamePlaySession(sessionDateTime, scoringModel, creator, game);

            }

            else {
                throw new NotFoundException("Board Game Not Found.");
            }


        }

        else {
            throw new CustomAccessDeniedException("You are not authorized to create a Game Play Session on another user's behalf");
        }


    }

    // Create SessionParticipants Helper Method
    private Set<SessionParticipant> createSessionParticipantsSet(Set<SessionParticipantDTO> sessionParticipantsDTOSet, Long authUserId) throws CustomAccessDeniedException, SessionParticipantTeamMismatchException {

        Set<SessionParticipant> sessionParticipantsSet = new HashSet<>();

        RegisteredPlayer authorizedUser = registeredPlayerRepository.getById(authUserId);

        boolean authorizedIsParticipant = sessionParticipantsDTOSet.stream().anyMatch(sessionParticipantDTO -> sessionParticipantDTO.playerId().equals(authorizedUser.getId()));

        if (authorizedIsParticipant) {

            for (SessionParticipantDTO sessionParticipantDTO : sessionParticipantsDTOSet) {

                Long peerId = sessionParticipantDTO.playerId();


                if (playerRepository.existsById(peerId)) {

                    int result = sessionParticipantDTO.result();
                    Player peer = playerRepository.getById((peerId));




                    if (confirmedConnectionRepository.existsByPeerAAndPeerBOrPeerAAndPeerB(authorizedUser, peer, peer, authorizedUser) || authorizedUser.getAssociations().contains(ghostPlayerRepository.getReferenceById(peerId))) {
                        SessionParticipant sessionParticipant = new SessionParticipant(result, peer);
                        sessionParticipantsSet.add(sessionParticipant);
                    } else {
                        throw new CustomAccessDeniedException("Player is not in your network.");
                    }

                } else {

                    throw new NotFoundException("No Player Found");

                }

            }

            return sessionParticipantsSet;

        }

        else {
            throw new SessionParticipantTeamMismatchException("You must be a Session Participant in the Game Play Session");
        }

    }






    // Create SessionTeams Helper Method
    private Set<SessionTeam> createSessionTeamSet(Set<SessionTeamDTO> sessionTeamDTOSet, Set<SessionParticipant> sessionParticipantsSet) throws SessionParticipantTeamMismatchException {

        Set<SessionTeam> sessionTeamsSet = new HashSet<>();


        for (SessionTeamDTO sessionTeamDTO : sessionTeamDTOSet) {

            int teamResult = sessionTeamDTO.result();
            String teamName = sessionTeamDTO.teamName();

            // Constructor for SessionTeam
            SessionTeam sessionTeam = new SessionTeam(teamResult, teamName);

            for (Long playerId : sessionTeamDTO.playerIds()) {

                for (SessionParticipant sessionParticipant : sessionParticipantsSet) {
                    if (sessionParticipant.getPlayer().getId().equals(playerId) && sessionParticipant.getResult() == teamResult) {
                        sessionParticipant.setSessionTeam(sessionTeam);
                        sessionTeam.getTeamMembers().add(sessionParticipant);
                    }

                    else {
                        throw new SessionParticipantTeamMismatchException("Game Session Participants do not match the Session Team Memebers or the Sessions Participants results do not match that of the Team.");
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

        Set<Long> sessionParticipantIds = new HashSet<>();
        for (SessionParticipant sessionParticipant : gamePlaySession.getSessionParticipants()) {
            sessionParticipantIds.add(sessionParticipant.getId());
        }

        Set<Long> sessionTeamIds = new HashSet<>();
        for (SessionTeam sessionTeam : gamePlaySession.getSessionTeams()) {
            sessionTeamIds.add(sessionTeam.getId());
        }

        return new GamePlaySessionResponseDTO(sessionDateTime, scoringModel, gameName, sessionParticipantIds, sessionTeamIds);

    }

    // </editor-fold>


}
