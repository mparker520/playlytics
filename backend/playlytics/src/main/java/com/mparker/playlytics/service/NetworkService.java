package com.mparker.playlytics.service;

// Imports


import com.mparker.playlytics.dto.*;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import com.mparker.playlytics.repository.*;
import jdk.jfr.Registered;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NetworkService {


    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GhostPlayerRepository ghostPlayerRepository;
    private final ConnectionRequestRepository connectionRequestRepository;
    private final ConfirmedConnectionRepository confirmedConnectionRepository;
    private final BlockedRelationshipRepository blockedRelationshipRepository;


    public NetworkService(RegisteredPlayerRepository registeredPlayerRepository, GhostPlayerRepository ghostPlayerRepository, ConnectionRequestRepository connectionRequestRepository, ConfirmedConnectionRepository confirmedConnectionRepository, BlockedRelationshipRepository blockedRelationshipRepository) {
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
        this.connectionRequestRepository = connectionRequestRepository;
        this.confirmedConnectionRepository = confirmedConnectionRepository;
        this.blockedRelationshipRepository = blockedRelationshipRepository;
    }

    //</editor-fold>


    //<editor-fold desc = "Get Available Peer By Email or DisplayName">

    @Transactional(readOnly = true)
    public Optional<Set<RegisteredPlayerResponseDTO>> getAvailablePeersByFilter(Long registeredPlayerId, String filter) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        RegisteredPlayer peer = registeredPlayerRepository.getReferenceByLoginEmailOrDisplayName(filter, filter);

        boolean isConnection = confirmedConnectionRepository.existsByPeerAAndPeerBOrPeerAAndPeerB(registeredPlayer, peer, peer, registeredPlayer);
        boolean blockExists = blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(registeredPlayer, peer, peer, registeredPlayer);

        if (peer == null || isConnection || blockExists || Objects.equals(registeredPlayer.getId(), peer.getId())) {
            return Optional.empty();
        }

        else {

            Set<RegisteredPlayerResponseDTO> registeredPlayerResponseDTOSet = new HashSet<>();
            RegisteredPlayerResponseDTO registeredPlayerResponseDTO = new RegisteredPlayerResponseDTO(peer.getFirstName(), peer.getLastName(), peer.getAvatar(), peer.getLoginEmail(), peer.getDisplayName());
            registeredPlayerResponseDTOSet.add(registeredPlayerResponseDTO);
            return Optional.of(registeredPlayerResponseDTOSet);

        }


    }

    //</editor-fold>


    //<editor-fold desc = "Get All Available Peers">

    @Transactional(readOnly = true)
    public Optional<Set<RegisteredPlayerResponseDTO>> getAllAvailablePeers(Long registeredPlayerId) {

        RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        Set<RegisteredPlayerResponseDTO> registeredPlayerResponseDTOSet = new HashSet<>();


        List<RegisteredPlayer> allAvailableRegisteredPlayers = registeredPlayerRepository.findAllByIdNot(registeredPlayerId);

        for (RegisteredPlayer peer : allAvailableRegisteredPlayers) {

            boolean connectionExists = confirmedConnectionRepository.existsByPeerAAndPeerBOrPeerAAndPeerB(registeredPlayer, peer, peer, registeredPlayer);
            boolean blockExists = blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(registeredPlayer, peer, peer, registeredPlayer);

            if (!connectionExists && !blockExists) {

                RegisteredPlayerResponseDTO registeredPlayerResponseDTO = new RegisteredPlayerResponseDTO(peer.getFirstName(), peer.getLastName(), peer.getAvatar(), peer.getLoginEmail(), peer.getDisplayName());
                registeredPlayerResponseDTOSet.add(registeredPlayerResponseDTO);

            }
        }

        if (registeredPlayerResponseDTOSet.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(registeredPlayerResponseDTOSet);
        }



    }



    //</editor-fold>

    //<editor-fold desc = "Create Connection Request">

    @Transactional
    public Optional<ConnectionRequestResponseDTO> createConnectionRequest(Long registeredPlayerId, Long peerId) {

        RegisteredPlayer sender = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        RegisteredPlayer recipient = registeredPlayerRepository.getReferenceById(peerId);

        if (blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(sender, recipient, recipient, sender)) {
            return Optional.empty();
        }

        else {
            ConnectionRequest existingConnectionRequest = null;


            if((connectionRequestRepository.existsBySender_IdAndRecipient_Id(registeredPlayerId, peerId) || connectionRequestRepository.existsBySender_IdAndRecipient_Id(peerId, registeredPlayerId)) )  {
                existingConnectionRequest = connectionRequestRepository.getReferenceBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(registeredPlayerId, peerId, peerId, registeredPlayerId);
            }

            if(existingConnectionRequest != null && existingConnectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.PENDING)) {
                ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(existingConnectionRequest.getSender().getId(), existingConnectionRequest.getRecipient().getId(), existingConnectionRequest.getConnectionRequestStatus());
                return Optional.of(connectionRequestResponseDTO);
            }

            else {
                ConnectionRequest newConnectionRequest = new ConnectionRequest(sender, recipient, ConnectionRequestStatus.PENDING);
                connectionRequestRepository.save(newConnectionRequest);

                ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(sender.getId(), recipient.getId(), ConnectionRequestStatus.PENDING);
                return Optional.of(connectionRequestResponseDTO);

            }
        }




    }

    //</editor-fold>

    //<editor-fold desc = "Confirm Connection">

    @Transactional
    public Optional<ConfirmedConnectionResponseDTO> confirmConnection(Long registeredPlayerId, Long connectionRequestId) {

        ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);

        Long senderId = connectionRequest.getSender().getId();
        Long recipientId = connectionRequest.getRecipient().getId();

        if (registeredPlayerId.equals(recipientId)) {

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

            ConfirmedConnectionResponseDTO confirmedConnectionResponseDTO = new ConfirmedConnectionResponseDTO(peerAId, peerBId, connectionRequest.getConnectionRequestStatus());
            return Optional.of(confirmedConnectionResponseDTO);

        }

        return Optional.empty();

    }

    //</editor-fold>

    //<editor-fold desc = "Decline Connection Request">

    @Transactional
    public void declineConnectionRequest(Long registeredPlayerId, Long connectionRequestId) {
        ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);

        if (registeredPlayerId.equals(connectionRequest.getRecipient().getId())) {

            connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.DECLINED);

        }

    }

    //</editor-fold>

    //<editor-fold desc = "Block Player">
    @Transactional
    public BlockedRelationshipResponseDTO blockRegisteredPlayer(Long registeredPlayerId, Long blockedPlayerId) {

        // Change existing Requests to BLOCKED
        if (connectionRequestRepository.existsBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(registeredPlayerId, blockedPlayerId, blockedPlayerId, registeredPlayerId)){

            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(registeredPlayerId, blockedPlayerId, blockedPlayerId, registeredPlayerId);

            if (connectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.ACCEPTED)) {

                ConfirmedConnection confirmedConnection = confirmedConnectionRepository.getReferenceByConnectionRequest_Id(connectionRequest.getId());
                confirmedConnectionRepository.delete(confirmedConnection);

            }

            connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.BLOCKED);

        }

        // Create Blocked Entity
        RegisteredPlayer blocker = registeredPlayerRepository.getReferenceById(registeredPlayerId);
        RegisteredPlayer blocked = registeredPlayerRepository.getReferenceById(blockedPlayerId);
        BlockedRelationshipId blockedRelationshipId = new BlockedRelationshipId(registeredPlayerId, blockedPlayerId);
        BlockedRelationship blockedRelationship = new BlockedRelationship(blockedRelationshipId, blocker, blocked);
        blockedRelationshipRepository.save(blockedRelationship);

        return new BlockedRelationshipResponseDTO(registeredPlayerId, blockedPlayerId, true);

    }

    //</editor-fold>

    //<editor-fold desc = "Remove Block">

    @Transactional
    public void removeBlock(Long registeredPlayerId, Long blockerId, Long blockedId) {

        BlockedRelationshipId blockedRelationshipId = new BlockedRelationshipId(blockerId, blockedId);
        BlockedRelationship blockedRelationship = blockedRelationshipRepository.getReferenceById(blockedRelationshipId);

        if (registeredPlayerId.equals(blockerId)) {
            blockedRelationshipRepository.delete(blockedRelationship);
        }


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

    //<editor-fold desc = "Remove Connection">

    @Transactional
    public void removeConnection(Long registeredPlayerId, Long peerAId, Long peerBId) {

        if (registeredPlayerId.equals(peerAId) || registeredPlayerId.equals(peerBId)) {

            ConfirmedConnectionId confirmedConnectionId = new ConfirmedConnectionId(peerAId, peerBId);
            ConfirmedConnection confirmedConnection = confirmedConnectionRepository.getReferenceById(confirmedConnectionId);

            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(confirmedConnection.getConnectionRequest().getId());

            confirmedConnectionRepository.delete(confirmedConnection);
            connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.REVERSED);

        }

    }

    //</editor-fold>

    //<editor-fold desc = "Get Available GhostPlayers">

    @Transactional(readOnly = true)
    public Optional<Set<GhostPlayerResponseDTO>> getUnassociatedGhostPlayerByEmail(Long registeredPlayerId, String identifierEmail) {

        GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceByIdentifierEmail(identifierEmail);
        boolean isAssociate = registeredPlayerRepository.existsByIdAndAssociations(registeredPlayerId, ghostPlayer);

        if (ghostPlayer == null || isAssociate) {
            return Optional.empty();
        }

        else {

            GhostPlayerResponseDTO ghostPlayerResponseDTO =  new GhostPlayerResponseDTO(ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());
            Set<GhostPlayerResponseDTO> ghostPlayerResponseDTOSet = new HashSet<>();
            ghostPlayerResponseDTOSet.add(ghostPlayerResponseDTO);
            return Optional.of(ghostPlayerResponseDTOSet);

        }


    }

    @Transactional(readOnly = true)
    public Optional<Set<GhostPlayerResponseDTO>> getAllUnassociatedGhostPlayers(Long registeredPlayerId) {

        Set<GhostPlayerResponseDTO> ghostPlayerResponseDTOSet = new HashSet<>();

        List<GhostPlayer> allGhostPlayers = ghostPlayerRepository.findAll();
        for (GhostPlayer ghostPlayer : allGhostPlayers) {

            if (!registeredPlayerRepository.existsByIdAndAssociations(registeredPlayerId, ghostPlayer)) {

                GhostPlayerResponseDTO ghostPlayerResponseDTO = new GhostPlayerResponseDTO(ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());
                ghostPlayerResponseDTOSet.add(ghostPlayerResponseDTO);

            }
        }

        if (ghostPlayerResponseDTOSet.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(ghostPlayerResponseDTOSet);
        }


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
