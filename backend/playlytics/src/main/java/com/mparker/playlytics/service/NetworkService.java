package com.mparker.playlytics.service;

// Imports


import com.mparker.playlytics.dto.*;
import com.mparker.playlytics.entity.*;
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import com.mparker.playlytics.exception.CustomAccessDeniedException;
import com.mparker.playlytics.exception.ExistingResourceException;
import com.mparker.playlytics.exception.NotFoundException;
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
    public RegisteredPlayerResponseDTO getAvailablePeersByFilter(Long registeredPlayerId, String filter, Long authUserId) throws CustomAccessDeniedException, NotFoundException {

        if (registeredPlayerId.equals(authUserId)) {


            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
            RegisteredPlayer peer = registeredPlayerRepository.getReferenceByLoginEmailOrDisplayName(filter.replaceAll("\\s+", "").toLowerCase(), filter);

            boolean isConnection = confirmedConnectionRepository.existsByPeerAAndPeerBOrPeerAAndPeerB(registeredPlayer, peer, peer, registeredPlayer);
            boolean blockExists = blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(registeredPlayer, peer, peer, registeredPlayer);

            if (peer == null || isConnection || blockExists || Objects.equals(registeredPlayer.getId(), peer.getId())) {
                throw new NotFoundException("No registered players available for connection by that filter.");
            }

            else {
                return new RegisteredPlayerResponseDTO(peer.getFirstName(), peer.getLastName(), peer.getAvatar(), peer.getLoginEmail(), peer.getDisplayName());
            }

        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this Resource.");
        }

    }

    //</editor-fold>

   /* //<editor-fold desc = "Get All Available Peers">

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



    //</editor-fold>*/

    //<editor-fold desc = "Create Connection Request">

    @Transactional
    public ConnectionRequestResponseDTO createConnectionRequest(Long registeredPlayerId, Long peerId, Long authUserId) throws CustomAccessDeniedException, ExistingResourceException, NotFoundException {

        ConnectionRequestResponseDTO connectionRequestResponseDTO = null;

        if (registeredPlayerId.equals(authUserId)) {

            if (registeredPlayerId.equals(peerId)) {
                throw new NotFoundException("You cannot connect with yourself.");
            }

            else {

                RegisteredPlayer sender = registeredPlayerRepository.getReferenceById(registeredPlayerId);
                RegisteredPlayer recipient = registeredPlayerRepository.getReferenceById(peerId);

                if (blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(sender, recipient, recipient, sender)) {
                    throw new CustomAccessDeniedException("You do not have access to this Resource.");
                }

                else {

                    if(connectionRequestRepository.existsBySender_IdAndRecipient_IdOrSender_IdAndRecipient_Id(registeredPlayerId, peerId, peerId, registeredPlayerId) )  {
                        Set<ConnectionRequest> existingConnectionRequestsSet = connectionRequestRepository.findAllBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(registeredPlayerId, peerId, peerId, registeredPlayerId);

                        for (ConnectionRequest existingConnectionRequest : existingConnectionRequestsSet) {
                            if(existingConnectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.PENDING) || existingConnectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.ACCEPTED)) {
                                throw new ExistingResourceException("You already have a pending or accepted connection request with this player.");
                            }

                            else {
                                ConnectionRequest newConnectionRequest = new ConnectionRequest(sender, recipient, ConnectionRequestStatus.PENDING);
                                connectionRequestRepository.save(newConnectionRequest);

                                connectionRequestResponseDTO =  new ConnectionRequestResponseDTO(sender.getId(), recipient.getId(), ConnectionRequestStatus.PENDING);

                            }
                        }
                    }
                }
            }
            return connectionRequestResponseDTO;
        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this Resource.");
        }

    }

    //</editor-fold>

    //<editor-fold desc = "Confirm Connection">

    @Transactional
    public ConfirmedConnectionResponseDTO confirmConnection(Long registeredPlayerId, Long connectionRequestId, Long authUserId) throws CustomAccessDeniedException{

        if (registeredPlayerId.equals(authUserId)) {
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

                return new ConfirmedConnectionResponseDTO(peerAId, peerBId, connectionRequest.getConnectionRequestStatus());

            }

            else {
                throw new CustomAccessDeniedException("You cannot confirm a connection request that you sent.");
            }

        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this Resource.");
        }




    }

    //</editor-fold>

    //<editor-fold desc = "Decline Connection Request">

    @Transactional
    public void declineConnectionRequest(Long registeredPlayerId, Long connectionRequestId, Long authUserId) throws CustomAccessDeniedException {

        if (registeredPlayerId.equals(authUserId)) {

            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);
            if (registeredPlayerId.equals(connectionRequest.getRecipient().getId())) {

                connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.DECLINED);

            }
            else {
                throw new CustomAccessDeniedException("You do not have access to this Resource.");
            }
        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this Resource.");
        }



    }

    //</editor-fold>

    //<editor-fold desc = "Block Player">
    @Transactional
    public BlockedRelationshipResponseDTO blockRegisteredPlayer(Long registeredPlayerId, Long blockedPlayerId, Long authUserId) throws CustomAccessDeniedException {

        if (registeredPlayerId.equals(authUserId)) {

            // Change existing Requests to BLOCKED
            if (connectionRequestRepository.existsBySender_IdAndRecipient_IdOrSender_IdAndRecipient_Id(registeredPlayerId, blockedPlayerId, blockedPlayerId, registeredPlayerId)){

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

        else  {
            throw new CustomAccessDeniedException("You do not have access to this Resource.");
        }






    }

    //</editor-fold>

    //<editor-fold desc = "Remove Block">

    @Transactional
    public void removeBlock(Long registeredPlayerId, Long blockerId, Long blockedId, Long authUserId) throws CustomAccessDeniedException {

        if (registeredPlayerId.equals(authUserId)) {

            if (registeredPlayerId.equals(blockerId)) {
                BlockedRelationshipId blockedRelationshipId = new BlockedRelationshipId(blockerId, blockedId);
                BlockedRelationship blockedRelationship = blockedRelationshipRepository.getReferenceById(blockedRelationshipId);
                blockedRelationshipRepository.delete(blockedRelationship);
            }

            else {
                throw new CustomAccessDeniedException("You do not have access to this Resource.");
            }

        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this Resource.");
        }



    }

    //</editor-fold>

    //<editor-fold desc = " View Sent Connection Requests">

    @Transactional(readOnly = true)
    public Set<ConnectionRequestResponseDTO> getAllSentConnectionRequests(Long registeredPlayerId, Long authUserId) throws CustomAccessDeniedException {

        if (registeredPlayerId.equals(authUserId)) {
            Set<ConnectionRequest> allSentConnectionRequests = connectionRequestRepository.getAllBySender_IdAndConnectionRequestStatus(registeredPlayerId, ConnectionRequestStatus.PENDING);
            Set<ConnectionRequestResponseDTO> allSentConnectionRequestResponses = new HashSet<>();

            for (ConnectionRequest connectionRequest : allSentConnectionRequests) {

                ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(registeredPlayerId, connectionRequest.getRecipient().getId(), connectionRequest.getConnectionRequestStatus());
                allSentConnectionRequestResponses.add(connectionRequestResponseDTO);

            }

            return allSentConnectionRequestResponses;

        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }

    }

    //</editor-fold>

    //<editor-fold desc = " View Pending Connection Requests">

    @Transactional(readOnly = true)
    public Set<ConnectionRequestResponseDTO> getAllPendingConnectionRequests(Long registeredPlayerId, Long authUserId) throws CustomAccessDeniedException {

        if(registeredPlayerId.equals(authUserId)) {
            Set<ConnectionRequest> allPendingConnectionRequests = connectionRequestRepository.getAllByRecipient_IdAndConnectionRequestStatus(registeredPlayerId, ConnectionRequestStatus.PENDING);
            Set<ConnectionRequestResponseDTO> allPendingConnectionRequestResponses = new HashSet<>();

            for (ConnectionRequest connectionRequest : allPendingConnectionRequests) {

                ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(connectionRequest.getSender().getId(), registeredPlayerId, connectionRequest.getConnectionRequestStatus());
                allPendingConnectionRequestResponses.add(connectionRequestResponseDTO);

            }

            return allPendingConnectionRequestResponses;
        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }



    }

    //</editor-fold>

    //<editor-fold desc = "Cancel Sent ConnectionRequest">

    @Transactional
    public void cancelConnectionRequest(Long registeredPlayerId, Long connectionRequestId, Long authUserId) throws CustomAccessDeniedException {

        if (registeredPlayerId.equals(authUserId)) {

            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);
            if(connectionRequest.getSender().getId().equals(registeredPlayerId) && connectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.PENDING)) {

                connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.REVERSED);

            }

            else {
                throw new CustomAccessDeniedException("You do not have access to this resource.");
            }

        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }


    }

    //</editor-fold>

    //<editor-fold desc = "View All Connections">

    @Transactional(readOnly = true)
    public Set<ConfirmedConnectionResponseDTO> getAllConnections(Long registeredPlayerId, Long authUserId) throws CustomAccessDeniedException, NotFoundException {

        if (registeredPlayerId.equals(authUserId)) {

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

            if(!allConnectionResponses.isEmpty()) {
                return allConnectionResponses;
            }
            else {
                throw new NotFoundException("No confirmed Connections!");
            }



        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }



    }

    //</editor-fold>

    //<editor-fold desc = "Remove Connection">

    @Transactional
    public void removeConnection(Long registeredPlayerId, Long peerAId, Long peerBId, Long authUserId) throws CustomAccessDeniedException {

        if (registeredPlayerId.equals(authUserId)) {

            if (registeredPlayerId.equals(peerAId) || registeredPlayerId.equals(peerBId)) {

                ConfirmedConnectionId confirmedConnectionId = new ConfirmedConnectionId(peerAId, peerBId);
                ConfirmedConnection confirmedConnection = confirmedConnectionRepository.getReferenceById(confirmedConnectionId);

                ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(confirmedConnection.getConnectionRequest().getId());

                confirmedConnectionRepository.delete(confirmedConnection);
                connectionRequest.setConnectionRequestStatus(ConnectionRequestStatus.REVERSED);

            }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");}
        }


    }

    //</editor-fold>

    //<editor-fold desc = "Get Available GhostPlayers">

    @Transactional(readOnly = true)
    public GhostPlayerResponseDTO getUnassociatedGhostPlayerByEmail(Long registeredPlayerId, String identifierEmail, Long authUserId) throws CustomAccessDeniedException, NotFoundException{

        if(registeredPlayerId.equals(authUserId)) {

            GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceByIdentifierEmail(identifierEmail.replaceAll("\\s+", "").toLowerCase());
            boolean isAssociate = registeredPlayerRepository.existsByIdAndAssociations(registeredPlayerId, ghostPlayer);

            if (ghostPlayer == null || isAssociate) {
                throw new NotFoundException("No ghost players available for connection by that filter.");
            }

            else {

                return new GhostPlayerResponseDTO(ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());

            }
        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }

    }

    //</editor-fold>

   /* @Transactional(readOnly = true)
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

    //</editor-fold> */

    //<editor-fold desc = "Add Association">

    @Transactional
    public GhostPlayerResponseDTO addAssociation(Long registeredPlayerId, Long ghostPlayerId, Long authUserId) throws CustomAccessDeniedException, NotFoundException {

        if (registeredPlayerId.equals(authUserId)) {

            Optional <GhostPlayer> ghostPlayer = ghostPlayerRepository.findById(ghostPlayerId);
            if (ghostPlayer.isPresent()) {

                GhostPlayer ghostPlayerExists = ghostPlayer.get();
                boolean isAssociate = registeredPlayerRepository.existsByIdAndAssociations(registeredPlayerId, ghostPlayerExists);

                if (!isAssociate) {
                    RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
                    registeredPlayer.getAssociations().add(ghostPlayerExists);

                    return new GhostPlayerResponseDTO(ghostPlayerExists.getFirstName(), ghostPlayerExists.getLastName(), ghostPlayerExists.getAvatar(), ghostPlayerExists.getIdentifierEmail(), ghostPlayerExists.getCreator().getId());

                }
                else {
                    throw new NotFoundException("No ghost players available for connection by that filter.");
                }

            }

            else {
                throw new NotFoundException("No ghost players available for connection by that filter.");
            }


        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }

    }

    //</editor-fold>

    //<editor-fold desc = " View All Associations">

    @Transactional(readOnly = true)
    public Set<GhostPlayerResponseDTO> getAllAssociations(Long registeredPlayerId, Long authUserId) {

        if (registeredPlayerId.equals(authUserId)) {
            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
            Set<GhostPlayer> allAssociations = registeredPlayer.getAssociations();

            Set<GhostPlayerResponseDTO> allAssociationResponses = new HashSet<>();

            for (GhostPlayer ghostPlayer : allAssociations) {

                GhostPlayerResponseDTO ghostPlayerResponseDTO = new GhostPlayerResponseDTO(ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());
                allAssociationResponses.add(ghostPlayerResponseDTO);
            }

            return allAssociationResponses;
        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }



    }

    //</editor-fold>

    //<editor-fold desc = "Remove Associations">

    @Transactional
    public void removeAssociation(Long registeredPlayerId, Long ghostPlayerId, Long authUserId) throws CustomAccessDeniedException{

        if (registeredPlayerId.equals(authUserId)) {
            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
            GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceById(ghostPlayerId);

            registeredPlayer.getAssociations().remove(ghostPlayer);

        }

        else {
            throw new CustomAccessDeniedException("You do not have access to this resource.");
        }


    }

    //</editor-fold>



}
