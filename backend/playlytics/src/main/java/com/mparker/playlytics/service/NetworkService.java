package com.mparker.playlytics.service;

// Imports


import com.mparker.playlytics.dto.ConnectionRequestResponseDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.entity.ConnectionRequest;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import com.mparker.playlytics.repository.ConnectionRequestRepository;
import com.mparker.playlytics.repository.GhostPlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class NetworkService {


    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GhostPlayerRepository ghostPlayerRepository;
    private final ConnectionRequestRepository connectionRequestRepository;


    public NetworkService(RegisteredPlayerRepository registeredPlayerRepository, GhostPlayerRepository ghostPlayerRepository, ConnectionRequestRepository connectionRequestRepository) {
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.connectionRequestRepository = connectionRequestRepository;
    }

    //</editor-fold>

    //<editor-fold desc = " View Sent Connection Requests">

    @Transactional(readOnly = true)
    public Set<ConnectionRequestResponseDTO> getAllSentConnectionRequests(Long registeredPlayerId) {

        Set<ConnectionRequest> allSentConnectionRequests = connectionRequestRepository.getAllBySender_IdAndConnectionRequestStatus(registeredPlayerId, ConnectionRequestStatus.PENDING);
        Set<ConnectionRequestResponseDTO> allSentConnectionRequestResponses = new HashSet<>();

        for (ConnectionRequest connectionRequest : allSentConnectionRequests) {

            ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(registeredPlayerId, connectionRequest.getRecipient().getId(), connectionRequest.getConnectionRequestStatus());
            allSentConnectionRequestResponses.add(connectionRequestResponseDTO);

        }


        return allSentConnectionRequestResponses;

    }

    //</editor-fold>

    //<editor-fold desc = "Add Association">

    @Transactional
    public GhostPlayerResponseDTO addAssociation(Long registeredPlayerId, Long ghostPlayerId) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceById(ghostPlayerId);
        registeredPlayer.getAssociations().add(ghostPlayer);

        return new GhostPlayerResponseDTO(ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());

    }

    //</editor-fold>

    //<editor-fold desc = " View All Associations">

    @Transactional(readOnly = true)
    public Set<GhostPlayerResponseDTO> getAllAssociations(Long registeredPlayerId) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        Set<GhostPlayer> allAssociations = registeredPlayer.getAssociations();

        Set<GhostPlayerResponseDTO> allAssociationResponses = new HashSet<>();

        for (GhostPlayer ghostPlayer : allAssociations) {

            GhostPlayerResponseDTO ghostPlayerResponseDTO = new GhostPlayerResponseDTO(ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());
            allAssociationResponses.add(ghostPlayerResponseDTO);
        }

        return allAssociationResponses;

    }

    //</editor-fold>

    //<editor-fold desc = "Remove Associations">

    @Transactional
    public void removeAssociation(Long registeredPlayerId, Long ghostPlayerId) {

       RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
       GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceById(ghostPlayerId);

        registeredPlayer.getAssociations().remove(ghostPlayer);

    }

    //</editor-fold>

    //<editor-fold desc = "Cancel Sent ConnectionRequest">

    @Transactional
    public void cancelConnectionRequest(Long registeredPlayerId, Long connectionRequestId) {

        ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);
        if(connectionRequest.getSender().getId().equals(registeredPlayerId) && connectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.PENDING)) {

            connectionRequestRepository.delete(connectionRequest);


        }

    }

    //</editor-fold>

}
