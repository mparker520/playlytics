package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.RegisteredPlayerResponseDTO;
import com.mparker.playlytics.dto.RegisteredPlayerUpdateDTO;
import com.mparker.playlytics.entity.GamePlaySession;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.entity.SessionParticipant;
import com.mparker.playlytics.enums.GhostStatus;
import com.mparker.playlytics.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;


@Service

public class RegisteredPlayerService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GhostPlayerRepository ghostPlayerRepository;
    private final GamePlaySessionRepository gamePlaySessionRepository;
    private final SessionParticipantRepository sessionParticipantRepository;

    public RegisteredPlayerService(RegisteredPlayerRepository registeredPlayerRepository, GhostPlayerRepository ghostPlayerRepository, GamePlaySessionRepository gamePlaySessionRepository, SessionParticipantRepository sessionParticipantRepository) {
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.gamePlaySessionRepository = gamePlaySessionRepository;
        this.sessionParticipantRepository = sessionParticipantRepository;
    }

    //</editor-fold>

    //<editor-fold desc = "Update RegisteredPlayer">

    @Transactional
    public RegisteredPlayerUpdateDTO updateRegisteredPlayer(Long registeredPlayerId, RegisteredPlayerUpdateDTO registeredPlayerUpdateDTO) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);

        if(registeredPlayerUpdateDTO.firstName() != null) {
            registeredPlayer.setFirstName(registeredPlayerUpdateDTO.firstName());
        }

        if(registeredPlayerUpdateDTO.lastName() != null) {
            registeredPlayer.setLastName(registeredPlayerUpdateDTO.lastName());
        }

        if(registeredPlayerUpdateDTO.avatar() != null) {
            registeredPlayer.setAvatar(registeredPlayerUpdateDTO.avatar());
        }

        if(registeredPlayerUpdateDTO.displayName() != null) {
            registeredPlayer.setDisplayName(registeredPlayerUpdateDTO.displayName());
        }

        registeredPlayerRepository.save(registeredPlayer);

        return new RegisteredPlayerUpdateDTO(registeredPlayer.getFirstName(), registeredPlayer.getLastName(), registeredPlayer.getAvatar(), registeredPlayer.getDisplayName());

    }

    //</editor-fold>

    //<editor-fold desc = "Delete RegisteredPlayer">

    @Transactional
    public void deleteRegisteredPlayer(Long registeredPlayerId) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);

        // Convert to GhostPlayer
        if (ghostPlayerRepository.existsByLinkedRegisteredPlayer_Id(registeredPlayerId)) {

                GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceByLinkedRegisteredPlayer_Id(registeredPlayerId);
                ghostPlayer.setStatus(GhostStatus.DEACTIVATED);
                ghostPlayer.setLinkedRegisteredPlayer(null);
                ghostPlayer.setCreator(null);

               updatePlayerReferences(registeredPlayerId, ghostPlayer);
        }

        else {
            // Create Ghost player and convert play sessions to Ghost player
            String firstName = registeredPlayer.getFirstName();
            String lastName = registeredPlayer.getLastName();
            byte[] avatar = registeredPlayer.getAvatar();
            String email = registeredPlayer.getLoginEmail();

            GhostPlayer ghostPlayer = new GhostPlayer(firstName, lastName, avatar, email, GhostStatus.DEACTIVATED, null, null);
            ghostPlayerRepository.save(ghostPlayer);
            updatePlayerReferences(registeredPlayerId, ghostPlayer);
        }

        // Remove row from table

        registeredPlayerRepository.deleteById(registeredPlayerId);
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
