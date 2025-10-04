package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.*;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.exception.CustomAccessDeniedException;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.exception.SessionParticipantTeamMismatchException;
import com.mparker.playlytics.repository.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


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


    @Transactional
    public GamePlaySessionResponseDTO assembleGpSession(GamePlaySessionDTO gamePlaySessionDTO, Long authUserId) throws SessionParticipantTeamMismatchException{

        if(gamePlaySessionDTO.scoringModel() == ScoringModel.COOPERATIVE) {
            if(gamePlaySessionDTO.sessionParticipantDTOSet().size() < 2) {
                throw new SessionParticipantTeamMismatchException("There must be at least 2 players for a Cooperative Game");
            }
        }

        if(gamePlaySessionDTO.scoringModel() == ScoringModel.TEAM) {
            if(gamePlaySessionDTO.sessionParticipantDTOSet().size() < 3) {
                throw new SessionParticipantTeamMismatchException("Team Scoring Must have 3 or more players.");
            }

        }

       ArrayList<Integer> results = new ArrayList<>();
        for(SessionParticipantDTO sessionParticipantDTO: gamePlaySessionDTO.sessionParticipantDTOSet()) {
            int result = sessionParticipantDTO.result();
            results.add(result);
        }



        if(gamePlaySessionDTO.scoringModel() == ScoringModel.RANKING) {
            if(!results.contains(1)) {
                throw new SessionParticipantTeamMismatchException("There must be at least one winner!");
            }

        }


            // Create GamePlaySession
            GamePlaySession gamePlaySession = createGpSession(gamePlaySessionDTO, authUserId);

            // Create SessionParticipants from sessionParticipantDTO Set
            Set<SessionParticipant> sessionParticipantSet = createSessionParticipantsSet(gamePlaySessionDTO.sessionParticipantDTOSet(), authUserId);


          /*  // Create SessionTeams from sessionTeamsDTOList If Scoring Model TEAMS and sessionTeamsDTOList not Empty
            if (gamePlaySession.getScoringModel() == ScoringModel.TEAM) {

                // Create Session Teams
                Set<SessionTeam> sessionTeamSet = createSessionTeamSet(gamePlaySessionDTO.sessionTeamDTOSet(), sessionParticipantSet, gamePlaySessionDTO.sessionParticipantDTOSet());

                // Link Teams and GamePlaySession
                linkTeamsAndGpSession(gamePlaySession, sessionTeamSet);


            } */


            // Link GamePlaySession and SessionParticipants together
            linkGpSessionAndParticipants(gamePlaySession, sessionParticipantSet);

            // Save GamePlaySession
            gamePlaySessionRepository.save(gamePlaySession);

            // Create and Return GamePlaySessionResponse DTO
            return createGpSessionResponseDTO(gamePlaySession);
        }






    //</editor-fold>

//<editor-fold desc="Get Methods">



    //<editor-fold desc="List all GamePlaySessions for a Registered Player">

    @Transactional (readOnly = true)
    public List<GameResponseDTO> getAllPlayedGames(Long authUserId) throws CustomAccessDeniedException{

        return gamePlaySessionRepository.getAllPlayedGames(authUserId);

    }
    //</editor-fold>


    //<editor-fold desc="List all GamePlaySessions for a Registered Player">


    //<editor-fold desc="List all GamePlaySessions for User by Game and Time">
    @Transactional (readOnly = true)
    public List<GamePlaySessionResponseDTO> findAllByPlayerIdAndParams(Long authenticatedUserId, Long gameId, String startDate, String endDate) {

        List<GamePlaySessionResponseDTO> gamePlaySessionResponseDTOSet = new ArrayList<>();


        LocalDate startDateConverted = LocalDate.parse(startDate);
        LocalDate endDateConverted = LocalDate.parse(endDate);
        LocalDateTime startDateTime = startDateConverted.atStartOfDay();
        LocalDateTime endDateTime = endDateConverted.atTime(LocalTime.MAX);

        List<GamePlaySession> gamePlaySessions = gamePlaySessionRepository.findAllPlayerIdAndParams(authenticatedUserId, gameId, startDateTime, endDateTime);

        for (GamePlaySession gamePlaySession : gamePlaySessions) {
            gamePlaySessionResponseDTOSet.add(createGpSessionResponseDTO(gamePlaySession));
        }

        return gamePlaySessionResponseDTOSet;

    }
    //</editor-fold>



    //<editor-fold desc="List all Pending GamePlaySessions for a Registered Player">

    @Transactional (readOnly = true)
    public Set<GamePlaySessionResponseDTO> getAllPendingGpSessions(Long authUserId) throws CustomAccessDeniedException, NotFoundException{


        Set<GamePlaySessionResponseDTO> gamePlaySessionResponseDTOSet = new HashSet<>();
        //RegisteredPlayer registeredPlayer = registeredPlayerRepository.findById(authUserId).orElseThrow(() -> new NotFoundException("No registered player found"));

        GhostPlayer ghostPlayer = ghostPlayerRepository.findByLinkedRegisteredPlayer_Id(authUserId);

        Set<SessionParticipant> associatedSessionParticipants = sessionParticipantRepository.findAllByPlayer_Id(ghostPlayer.getId());

        for (SessionParticipant sessionParticipant : associatedSessionParticipants) {
            GamePlaySession gamePlaySession = sessionParticipant.getGamePlaySession();
            gamePlaySessionResponseDTOSet.add(createGpSessionResponseDTO(gamePlaySession));
        }

        return gamePlaySessionResponseDTOSet;

    }

    //</editor-fold>


//</editor-fold>

//<editor-fold desc="POST Methods">
    //<editor-fold desc="Accept Pending Game Play Sessions">
    @Transactional
    public void acceptGamePlaySession(Long id, Long authUserId) throws NotFoundException {

        RegisteredPlayer player = registeredPlayerRepository.findById(authUserId).orElseThrow(() -> new NotFoundException("Player doesn't exist."));
        GamePlaySession gamePlaySession = gamePlaySessionRepository.findById(id).orElseThrow(() -> new NotFoundException("Session doesn't exist."));
        GhostPlayer ghostPlayer = ghostPlayerRepository.findByLinkedRegisteredPlayer_Id(authUserId);
        Set<SessionParticipant> sessionParticipants = gamePlaySession.getSessionParticipants();

        for(SessionParticipant sessionParticipant : sessionParticipants) {
            if(sessionParticipant.getPlayer().getId().equals(ghostPlayer.getId())) {
                sessionParticipant.setPlayer(player);
            }
        }

    }
    //</editor-fold>
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

//<editor-fold desc="Helper Methods">


//<editor-fold desc="Create GamePlaySession Helper Method">

    private GamePlaySession createGpSession(GamePlaySessionDTO gamePlaySessionDTO, Long authUserId) throws CustomAccessDeniedException, NotFoundException {



            Long gameId = gamePlaySessionDTO.gameId();

            if (gameRepository.existsById(gameId)) {

                Instant sessionDateTime = gamePlaySessionDTO.sessionDateTime();
                ScoringModel scoringModel = gamePlaySessionDTO.scoringModel();
                Player creator = playerRepository.getReferenceById(authUserId);
                Game game = gameRepository.getReferenceById(gameId);

                return new GamePlaySession(sessionDateTime, scoringModel, creator, game);

            }

            else {
                throw new NotFoundException("Board Game Not Found.");
            }


    }


//</editor-fold>

//<editor-fold desc="Create SessionParticipants Helper Method">

    private Set<SessionParticipant> createSessionParticipantsSet(Set<SessionParticipantDTO> sessionParticipantsDTOSet, Long authUserId) throws CustomAccessDeniedException, SessionParticipantTeamMismatchException {

        Set<SessionParticipant> sessionParticipantsSet = new HashSet<>();

        RegisteredPlayer authorizedUser = registeredPlayerRepository.getById(authUserId);

        boolean authorizedIsParticipant = sessionParticipantsDTOSet.stream().anyMatch(sessionParticipantDTO -> sessionParticipantDTO.playerId().equals(authorizedUser.getId()));

        if (authorizedIsParticipant) {

            for (SessionParticipantDTO sessionParticipantDTO : sessionParticipantsDTOSet) {

                Long peerId = sessionParticipantDTO.playerId();

                // Check that Player exists
                if (playerRepository.existsById(peerId)) {

                    int result = sessionParticipantDTO.result();
                    Player peer = playerRepository.getById(peerId);


                   // If Peer is a Registered Player, Check If Self or Confirmed Connection
                    if(Hibernate.getClass(peer) == RegisteredPlayer.class) {


                        if (confirmedConnectionRepository.existsByPeerAAndPeerBOrPeerAAndPeerB(authorizedUser, peer, peer, authorizedUser) || peerId.equals(authUserId)) {
                            SessionParticipant sessionParticipant = new SessionParticipant(result, peer);
                            sessionParticipantsSet.add(sessionParticipant);

                        } else {
                            throw new CustomAccessDeniedException("Player is not in your network.");
                        }

                    }
                    // If Peer is Ghost Player, Check Association

                    else {

                        Optional<GhostPlayer> ghostPlayer = ghostPlayerRepository.findById(peerId);
                        if(ghostPlayer.isPresent()) {
                            GhostPlayer existingGhostPlayer = ghostPlayer.get();
                            if(authorizedUser.getAssociations().contains(existingGhostPlayer)) {
                                SessionParticipant sessionParticipant = new SessionParticipant(result, existingGhostPlayer);
                                sessionParticipantsSet.add(sessionParticipant);
                            }
                            else {
                                throw new CustomAccessDeniedException("Player is not in your network.");
                            }
                        }
                        else {
                            throw new NotFoundException("No Ghost Player Found.");
                        }

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
//</editor-fold>

//<editor-fold desc="Create Session Teams Helper Method">

  /*  private Set<SessionTeam> createSessionTeamSet(Set<SessionTeamDTO> sessionTeamDTOSet, Set<SessionParticipant> sessionParticipantsSet, Set<SessionParticipantDTO> sessionParticipantDTOSet) throws SessionParticipantTeamMismatchException {

        // Session Teams to be Returned
        Set<SessionTeam> sessionTeamsSet = new HashSet<>();



                // For Each Session Team
                for (SessionTeamDTO sessionTeamDTO : sessionTeamDTOSet) {

                    // Create Team with Result and Name
                    int teamResult = sessionTeamDTO.result();
                    String teamName = sessionTeamDTO.teamName();
                    SessionTeam sessionTeam = new SessionTeam(teamResult, teamName);

                    for(SessionParticipant sessionParticipant : sessionParticipantsSet) {

                        for (SessionParticipantDTO sessionParticipantDTO : sessionParticipantDTOSet) {

                            if (sessionParticipantDTO.playerId().equals(sessionParticipant.getPlayer().getId())) {

                                int sessionParticipantTeamIndex = sessionParticipantDTO.teamIndex();

                                if (sessionParticipantTeamIndex == sessionTeamDTO.teamIndex()) {

                                    sessionTeam.getTeamMembers().add(sessionParticipant);
                                }
                            }
                        }
                    }

                    sessionTeamsSet.add(sessionTeam);

                }

                for (SessionTeam sessionTeam : sessionTeamsSet) {

                    for(SessionParticipant teamSessionParticipant : sessionTeam.getTeamMembers()) {

                        for(SessionParticipant sessionParticipant : sessionParticipantsSet) {
                            if (sessionParticipant.getPlayer().getId().equals(teamSessionParticipant.getPlayer().getId())) {

                                sessionParticipant.setResult(sessionTeam.getResult());
                                sessionParticipant.setSessionTeam(sessionTeam);

                            }
                        }


                    }




                }


                return sessionTeamsSet;


    } */

//</editor-fold>


//<editor-fold desc="Attach GamePlaySession and SessionParticipants Helper Method">

    private void linkGpSessionAndParticipants(GamePlaySession gamePlaySession, Set<SessionParticipant> sessionParticipantsSet) {

        for (SessionParticipant sessionParticipant : sessionParticipantsSet) {
            sessionParticipant.setGamePlaySession(gamePlaySession);
            gamePlaySession.getSessionParticipants().add(sessionParticipant);
        }

    }
//</editor-fold>

//<editor-fold desc="Attach GamePlaySession and SessionTeams Helper Method">

    /* private void linkTeamsAndGpSession(GamePlaySession gamePlaySession, Set<SessionTeam> sessionTeamSet) {
        for (SessionTeam sessionTeam : sessionTeamSet) {
            sessionTeam.setGamePlaySession(gamePlaySession);
            gamePlaySession.getSessionTeams().add(sessionTeam);
        }
    } */
//</editor-fold>

//<editor-fold desc="Create GamePlaySessionResponseDTO Helper Method">

    private GamePlaySessionResponseDTO createGpSessionResponseDTO(GamePlaySession gamePlaySession) {
        Long id = gamePlaySession.getId();
        Instant sessionDateTime = gamePlaySession.getSessionDateTime();
        ScoringModel scoringModel = gamePlaySession.getScoringModel();
        String gameName = gamePlaySession.getGame().getGameTitle();

        Set<PlayerResponseDTO> sessionParticipantDetails = new HashSet<>();
        for(SessionParticipant sessionParticipant : gamePlaySession.getSessionParticipants()) {

            String firstName = sessionParticipant.getPlayer().getFirstName();
            String lastName = sessionParticipant.getPlayer().getLastName();
            String identifier;

            Long playerId = sessionParticipant.getPlayer().getId();
            Player proxyPlayer = playerRepository.getById(playerId);
            Player player = Hibernate.unproxy(proxyPlayer, Player.class);

            if (player instanceof RegisteredPlayer registeredPlayer) {
                identifier = registeredPlayer.getDisplayName();
            }
            else {
                GhostPlayer ghostPlayer = (GhostPlayer) player;
                identifier = ghostPlayer.getIdentifierEmail();
            }



            PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO(playerId, firstName, lastName, identifier);
            sessionParticipantDetails.add(playerResponseDTO);
        }

        return new GamePlaySessionResponseDTO(id, sessionDateTime, scoringModel, gameName, sessionParticipantDetails);

    }



//</editor-fold>





//</editor-fold>

}

