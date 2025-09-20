package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GhostPlayerDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.dto.GhostPlayerUpdateDTO;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.enums.GhostStatus;
import com.mparker.playlytics.exception.CustomAccessDeniedException;
import com.mparker.playlytics.exception.ExistingResourceException;
import com.mparker.playlytics.repository.GhostPlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service

public class GhostPlayerService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final GhostPlayerRepository ghostPlayerRepository;
    private final RegisteredPlayerRepository registeredPlayerRepository;

    public GhostPlayerService(final GhostPlayerRepository ghostPlayerRepository, final RegisteredPlayerRepository registeredPlayerRepository) {
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.registeredPlayerRepository = registeredPlayerRepository;
    }

    //</editor-fold>

    // <editor-fold desc = "Create New GhostPlayer">

    @Transactional()
    public GhostPlayerResponseDTO createNewGhostPlayer(GhostPlayerDTO ghostPlayerDTO, Long authUserId) throws ExistingResourceException, CustomAccessDeniedException {

        String identifierEmail = ghostPlayerDTO.identifierEmail().replaceAll("\\s+", "").toLowerCase();

        if (ghostPlayerRepository.existsByIdentifierEmail(identifierEmail)) {
            throw new ExistingResourceException("Ghost player with email " + identifierEmail + "already exists");
        }

        else if (registeredPlayerRepository.existsByLoginEmail(identifierEmail)) {
            throw new ExistingResourceException("Registered player with email " + identifierEmail + "already exists.  Please send Connection Request");
        }

        else {

            Long linkedRegisteredPlayerId = ghostPlayerDTO.linkedRegisteredPlayerId();

            if(linkedRegisteredPlayerId == null) {



                    RegisteredPlayer creator = registeredPlayerRepository.getReferenceById(authUserId);
                    String firstName = ghostPlayerDTO.firstName();
                    String lastName = ghostPlayerDTO.lastName();
                    byte[] avatar = ghostPlayerDTO.avatar();
                    GhostStatus status = GhostStatus.NEW;


                    // Create GhostPlayer
                    GhostPlayer ghostPlayer = new GhostPlayer(firstName, lastName, avatar, identifierEmail, status, null, creator);

                    // Save GhostPlayer
                    ghostPlayerRepository.save(ghostPlayer);

                    // Add Association to Creator
                    creator.getAssociations().add(ghostPlayer);

                    // Return GhostPlayerDTO
                    return createGhostPlayerResponseDTO(ghostPlayer);


            }

            else {
                throw new ExistingResourceException("A New Ghost Player Cannot  Be Created That Has an Existing Registered Player");
            }

        }

    }


    //</editor-fold>

    //<editor-fold desc = "Update GhostPlayer">

    @Transactional
    public GhostPlayerResponseDTO updateGhostPlayer(Long ghostPlayerId, GhostPlayerUpdateDTO ghostPlayerUpdateDTO, Long authUserId) throws CustomAccessDeniedException {

        // Retrieve GhostPlayer from GhostPlayerRepository
        GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceById(ghostPlayerId);

        // If Current Player is Creator of GhostPlayer, Allow for Update
        if (ghostPlayer.getCreator().getId().equals(authUserId) && !ghostPlayer.getStatus().equals(GhostStatus.UPGRADED)) {

            if (ghostPlayerUpdateDTO.firstName() != null) {
                ghostPlayer.setFirstName(ghostPlayerUpdateDTO.firstName());
            }

            if (ghostPlayerUpdateDTO.lastName() != null) {
                ghostPlayer.setLastName(ghostPlayerUpdateDTO.lastName());
            }

            if (ghostPlayerUpdateDTO.avatar() != null) {
                ghostPlayer.setAvatar(ghostPlayerUpdateDTO.avatar());
            }

            if (ghostPlayerUpdateDTO.identifierEmail() != null) {
                ghostPlayer.setIdentifierEmail(ghostPlayerUpdateDTO.identifierEmail().replaceAll("\\s+", "").toLowerCase());
            }

            return createGhostPlayerResponseDTO(ghostPlayer);

        }

        else {
            throw new CustomAccessDeniedException("You Do Not Have Permission to Update This Ghost Player");
        }



    }

    //</editor-fold>

    //<editor-fold desc = "Helper Method">

    private GhostPlayerResponseDTO createGhostPlayerResponseDTO(GhostPlayer ghostPlayer) {

        String firstName = ghostPlayer.getFirstName();
        String lastName = ghostPlayer.getLastName();
        byte[] avatar = ghostPlayer.getAvatar();
        String identifierEmail = ghostPlayer.getIdentifierEmail();
        Long creatorId = ghostPlayer.getCreator().getId();
        Long id = ghostPlayer.getId();


        return new GhostPlayerResponseDTO(id, firstName, lastName, avatar, identifierEmail, creatorId);

    }

    //</editor-fold>

    // Does Not Support Deletion of GhostPlayer

}
