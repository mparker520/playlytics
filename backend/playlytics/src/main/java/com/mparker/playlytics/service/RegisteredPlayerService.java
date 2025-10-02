package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.PlayerResponseDTO;
import com.mparker.playlytics.dto.RegisteredPlayerDTO;
import com.mparker.playlytics.dto.RegisteredPlayerResponseDTO;
import com.mparker.playlytics.dto.RegisteredPlayerUpdateDTO;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.GhostStatus;
import com.mparker.playlytics.exception.CustomAccessDeniedException;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;


@Service

public class RegisteredPlayerService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GhostPlayerRepository ghostPlayerRepository;
    private final GamePlaySessionRepository gamePlaySessionRepository;
    private final SessionParticipantRepository sessionParticipantRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisteredPlayerService(RegisteredPlayerRepository registeredPlayerRepository, GhostPlayerRepository ghostPlayerRepository,
                                   GamePlaySessionRepository gamePlaySessionRepository, SessionParticipantRepository sessionParticipantRepository,
                                   PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.gamePlaySessionRepository = gamePlaySessionRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //</editor-fold>


    //<editor-fold desc="GET Profile Info">
    public RegisteredPlayerResponseDTO getProfile(Long authUserId) throws NotFoundException {
        RegisteredPlayer self = registeredPlayerRepository.findById(authUserId).orElse(null);
        if (self != null) {

            return new RegisteredPlayerResponseDTO(self.getId(), self.getFirstName(), self.getLastName(), null, self.getLoginEmail(), self.getDisplayName());
        }

        else {
            throw new NotFoundException("There is no player by that id");
        }

    }
    //</editor-fold>


    //<editor-fold desc = "Create Registered Player">
    @Transactional
    public RegisteredPlayerResponseDTO createRegisteredPlayer(RegisteredPlayerDTO registeredPlayerDTO) throws CustomAccessDeniedException {

        String loginEmail = registeredPlayerDTO.loginEmail().replaceAll("\\s+", "").toLowerCase();

        if (!registeredPlayerRepository.existsByLoginEmail(loginEmail)) {


            String encodedPassword = passwordEncoder.encode(registeredPlayerDTO.password());

            String firstName = registeredPlayerDTO.firstName();
            String lastName = registeredPlayerDTO.lastName();
            String displayName = registeredPlayerDTO.displayName();
            byte[] avatar = registeredPlayerDTO.avatar();

            RegisteredPlayer registeredPlayer = new RegisteredPlayer(firstName, lastName, avatar,  displayName, loginEmail, (encodedPassword));
            Long newRegisteredPlayerId = registeredPlayerRepository.saveAndFlush(registeredPlayer).getId();
            if(ghostPlayerRepository.existsByIdentifierEmail(loginEmail)) {
                    GhostPlayer ghostPlayer = ghostPlayerRepository.findByIdentifierEmail(loginEmail);
                    ghostPlayer.setLinkedRegisteredPlayer(registeredPlayer);
            }
            return new RegisteredPlayerResponseDTO(newRegisteredPlayerId, firstName, lastName, avatar, loginEmail, displayName);

        }

        else {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "An Account with that login email already exists"
                );
        }

    }

    //</editor-fold>

    //<editor-fold desc = "Update RegisteredPlayer">

    @Transactional
    public RegisteredPlayerUpdateDTO updateRegisteredPlayer(Long authUserId, RegisteredPlayerUpdateDTO registeredPlayerUpdateDTO) throws CustomAccessDeniedException {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(authUserId);

        if(registeredPlayerUpdateDTO.firstName() != null && !registeredPlayerUpdateDTO.firstName().isBlank()) {
            registeredPlayer.setFirstName(registeredPlayerUpdateDTO.firstName());
        }

        if(registeredPlayerUpdateDTO.lastName() != null && !registeredPlayerUpdateDTO.lastName().isBlank()) {
            registeredPlayer.setLastName(registeredPlayerUpdateDTO.lastName());
        }


        if(registeredPlayerUpdateDTO.displayName() != null && !registeredPlayerUpdateDTO.displayName().isBlank()) {
            registeredPlayer.setDisplayName(registeredPlayerUpdateDTO.displayName());
        }

        if(registeredPlayerUpdateDTO.loginEmail() != null && !registeredPlayerUpdateDTO.loginEmail().isBlank()) {
            if(registeredPlayerRepository.existsByLoginEmail(registeredPlayerUpdateDTO.loginEmail())) {
                throw new CustomAccessDeniedException("An Account with that login email already exists");
            }
            registeredPlayer.setDisplayName(registeredPlayerUpdateDTO.displayName());
        }

        registeredPlayerRepository.save(registeredPlayer);

        return new RegisteredPlayerUpdateDTO(registeredPlayer.getFirstName(), registeredPlayer.getLastName(), registeredPlayer.getDisplayName(), registeredPlayer.getLoginEmail());

    }

    //</editor-fold>

    //<editor-fold desc = "Delete RegisteredPlayer">

    @Transactional
    public void deleteRegisteredPlayer(Long authUserId) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.findById(authUserId).orElseThrow(() -> new NotFoundException("There is no player by that id"));

        // Convert to GhostPlayer
        if (ghostPlayerRepository.existsByLinkedRegisteredPlayer_Id(authUserId)) {

                GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceByLinkedRegisteredPlayer_Id(authUserId);
                ghostPlayer.setStatus(GhostStatus.REACTIVATED);
                ghostPlayer.setLinkedRegisteredPlayer(null);
                ghostPlayer.setCreator(null);

               updatePlayerReferences(authUserId, ghostPlayer);
        }

        else {
            // Create Ghost player and convert play sessions to Ghost player
            String firstName = registeredPlayer.getFirstName();
            String lastName = registeredPlayer.getLastName();
            byte[] avatar = registeredPlayer.getAvatar();
            String email = registeredPlayer.getLoginEmail();

            GhostPlayer ghostPlayer = new GhostPlayer(firstName, lastName, avatar, email, GhostStatus.FROMDELETE, null, null);
            ghostPlayerRepository.save(ghostPlayer);
            updatePlayerReferences(authUserId, ghostPlayer);
        }

        // Remove row from table
        registeredPlayerRepository.delete(registeredPlayer);



    }

    //</editor-fold>

    //<editor-fold desc = "Helper Methods">

    public void updatePlayerReferences(Long registeredPlayerId, GhostPlayer ghostPlayer) {

        // Update PlayerId in Session Participants
        Set<SessionParticipant> sessionParticipantSet = sessionParticipantRepository.findAllByPlayer_Id(registeredPlayerId);
        for (SessionParticipant sessionParticipant : sessionParticipantSet) {
            sessionParticipant.setPlayer(ghostPlayer);
        }

        // Update CreatorId in GamePlaySessions
        Set<GamePlaySession> createdGamePlaySessions = gamePlaySessionRepository.findAllByCreator_Id(registeredPlayerId);
        for (GamePlaySession gamePlaySession : createdGamePlaySessions) {
            gamePlaySession.setCreator(ghostPlayer);
        }

        // Update CreatorId in GhostPlayers
        Set<GhostPlayer> createdGhostPlayers = ghostPlayerRepository.findAllByCreator_Id(registeredPlayerId);
        for (GhostPlayer createdGhostPlayer : createdGhostPlayers) {
            createdGhostPlayer.setCreator(null);
        }

    }


    //</editor-fold>



}
