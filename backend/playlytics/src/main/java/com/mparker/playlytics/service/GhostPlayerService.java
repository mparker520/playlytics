package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GhostPlayerDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.enums.GhostStatus;
import com.mparker.playlytics.repository.GhostPlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service

public class GhostPlayerService {

    //<editor-fold desc = "Constructors and Dependencies">

    public final GhostPlayerRepository ghostPlayerRepository;
    public final RegisteredPlayerRepository registeredPlayerRepository;

    public GhostPlayerService(final GhostPlayerRepository ghostPlayerRepository, final RegisteredPlayerRepository registeredPlayerRepository) {
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.registeredPlayerRepository = registeredPlayerRepository;
    }

    //</editor-fold>

    //<editor-fold desc = "GhostPlayer Helper Methods">

    private GhostPlayerResponseDTO createGhostPlayerResponseDTO(GhostPlayer ghostPlayer) {

        String firstName = ghostPlayer.getFirstName();
        String lastName = ghostPlayer.getLastName();
        byte[] avatar = ghostPlayer.getAvatar();
        String identifierEmail = ghostPlayer.getIdentifierEmail();
        Long creatorId = ghostPlayer.getCreator().getId();


        return new GhostPlayerResponseDTO(firstName, lastName, avatar, identifierEmail, creatorId);

    }

    //</editor-fold>

    // <editor-fold desc = "Create GhostPlayer">

    // Create GhostPlayer (Method for RegisteredPlayer to create GhostPlayer from Scratch)
    @Transactional()
    private GhostPlayerResponseDTO createNewGhostPlayer(GhostPlayerDTO ghostPlayerDTO) {

        // Initialize Fields from DTO
        String firstName = ghostPlayerDTO.firstName();
        String lastName = ghostPlayerDTO.lastName();
        byte[] avatar = ghostPlayerDTO.avatar();
        String identifierEmail = ghostPlayerDTO.identifierEmail().replaceAll("\\s+", "").toLowerCase();
        GhostStatus status = ghostPlayerDTO.status();
        Long registeredPlayerId = ghostPlayerDTO.registeredPlayerId();
        Long creatorId = ghostPlayerDTO.creatorId();
        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        RegisteredPlayer creator = registeredPlayerRepository.getReferenceById(creatorId);

        // Create GhostPlayer
        GhostPlayer ghostPlayer = new GhostPlayer(firstName, lastName, avatar, identifierEmail, status, registeredPlayer, creator);

        // Save GhostPlayer
        ghostPlayerRepository.save(ghostPlayer);

        // Return GhostPlayerDTO
        return createGhostPlayerResponseDTO(ghostPlayer);

    }


    //</editor-fold>

    //<editor-fold desc = "Update GhostPlayer">

    //</editor-fold>

    // <editor-fold desc = "Lookup GhostPlayer">

    @Transactional(readOnly = true)
    private GhostPlayerResponseDTO findGhostPlayerByIdentifierEmail(String identifierEmail) {

        String identifierEmailNormalized = identifierEmail.replaceAll("\\s+", "").toLowerCase();
        GhostPlayer ghostPlayer = ghostPlayerRepository.findGhostPlayerByIdentifierEmail(identifierEmailNormalized);

        return createGhostPlayerResponseDTO(ghostPlayer);

    }

    //</editor-fold>

    // Does Not Support Deletion of GhostPlayer

}
