package com.mparker.playlytics.service;

// Imports


import com.mparker.playlytics.dto.ConfirmedConnectionResponseDTO;
import com.mparker.playlytics.dto.ConnectionRequestResponseDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import com.mparker.playlytics.repository.ConfirmedConnectionRepository;
import com.mparker.playlytics.repository.ConnectionRequestRepository;
import com.mparker.playlytics.repository.GhostPlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class NetworkService {


    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GhostPlayerRepository ghostPlayerRepository;
    private final ConnectionRequestRepository connectionRequestRepository;
    private final ConfirmedConnectionRepository confirmedConnectionRepository;


    public NetworkService(RegisteredPlayerRepository registeredPlayerRepository, GhostPlayerRepository ghostPlayerRepository, ConnectionRequestRepository connectionRequestRepository, ConfirmedConnectionRepository confirmedConnectionRepository) {
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.connectionRequestRepository = connectionRequestRepository;
        this.confirmedConnectionRepository = confirmedConnectionRepository;
    }

    //</editor-fold>

    //<editor-fold desc = "Create Connection Request">

    @Transactional
    public ConnectionRequestResponseDTO createConnectionRequest(Long registeredPlayerId, Long peerId) {

        RegisteredPlayer sender = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        RegisteredPlayer recipient = registeredPlayerRepository.getReferenceById(peerId);

        ConnectionRequest existingConnectionRequest = null;


        if((connectionRequestRepository.existsBySender_IdAndRecipient_Id(registeredPlayerId, peerId) || connectionRequestRepository.existsBySender_IdAndRecipient_Id(peerId, registeredPlayerId)) )  {
            existingConnectionRequest = connectionRequestRepository.getReferenceBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(registeredPlayerId, peerId, peerId, registeredPlayerId);
        }

        if(existingConnectionRequest != null && existingConnectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.PENDING)) {
            return new ConnectionRequestResponseDTO(existingConnectionRequest.getSender().getId(), existingConnectionRequest.getRecipient().getId(), existingConnectionRequest.getConnectionRequestStatus());
        }

        else {
            ConnectionRequest newConnectionRequest = new ConnectionRequest(sender, recipient, ConnectionRequestStatus.PENDING);
            connectionRequestRepository.save(newConnectionRequest);
            return new ConnectionRequestResponseDTO(sender.getId(), recipient.getId(), ConnectionRequestStatus.PENDING);
        }


    }

    //</editor-fold>

    //<editor-fold desc = "Confirm Connection">

    @Transactional
    public ConfirmedConnectionResponseDTO confirmConnection(Long registeredPlayerId, Long connectionRequestId) {

        ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);

        Long senderId = connectionRequest.getSender().getId();
        Long recipientId = connectionRequest.getRecipient().getId();

        Long peerAId;
        Long peerBId;

        if (senderId < recipientId) {
            peerAId = senderId;
            peerBId = recipientId;
        }
        else {
            peerAId = recipientId;
            peerBId = senderId;
        }

        // Create Confirmed Connection
        ConfirmedConnectionId confirmedConnectionId = new ConfirmedConnectionId(peerAId, peerBId);
        RegisteredPlayer peerA = registeredPlayerRepository.getReferenceById(peerAId);
        RegisteredPlayer peerB = registeredPlayerRepository.getReferenceById(peerBId);
        ConfirmedConnection confirmedConnection = new ConfirmedConnection(confirmedConnectionId, peerA, peerB, connectionRequest);

        // Save Confirmed Connection
        confirmedConnectionRepository.save(confirmedConnection);

        // Update Status of Linked ConnectionRequest to Accepted
        connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.ACCEPTED);

        return new ConfirmedConnectionResponseDTO(peerAId, peerBId, connectionRequest.getConnectionRequestStatus());

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

    //<editor-fold desc = " View Pending Connection Requests">

    @Transactional(readOnly = true)
    public Set<ConnectionRequestResponseDTO> getAllPendingConnectionRequests(Long registeredPlayerId) {

        Set<ConnectionRequest> allPendingConnectionRequests = connectionRequestRepository.getAllByRecipient_IdAndConnectionRequestStatus(registeredPlayerId, ConnectionRequestStatus.PENDING);
        Set<ConnectionRequestResponseDTO> allPendingConnectionRequestResponses = new HashSet<>();

        for (ConnectionRequest connectionRequest : allPendingConnectionRequests) {

            ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(connectionRequest.getSender().getId(), registeredPlayerId, connectionRequest.getConnectionRequestStatus());
            allPendingConnectionRequestResponses.add(connectionRequestResponseDTO);

        }

        return allPendingConnectionRequestResponses;

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

    //<editor-fold desc = "View All Connections">

    @Transactional(readOnly = true)
    public Set<ConfirmedConnectionResponseDTO> getAllConnections(Long registeredPlayerId) {

        Set<ConfirmedConnection> allConnections = confirmedConnectionRepository.getAllByPeerA_Id(registeredPlayerId);
        allConnections.addAll(confirmedConnectionRepository.getAllByPeerB_Id(registeredPlayerId));

        Set<ConfirmedConnectionResponseDTO> allConnectionResponses = new HashSet<>();

        for (ConfirmedConnection confirmedConnection : allConnections) {

            Long peerAId = confirmedConnection.getPeerA().getId();
            Long peerBId = confirmedConnection.getPeerB().getId();
            ConnectionRequestStatus connectionRequestStatus = confirmedConnection.getConnectionRequest().getConnectionRequestStatus();

           ConfirmedConnectionResponseDTO connectionRequestResponseDTO = new ConfirmedConnectionResponseDTO(peerAId, peerBId, connectionRequestStatus);
            allConnectionResponses.add(connectionRequestResponseDTO);

        }


        return allConnectionResponses;

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



}
