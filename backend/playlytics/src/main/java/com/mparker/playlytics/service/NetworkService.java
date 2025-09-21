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
    public RegisteredPlayerResponseDTO getAvailablePeersByFilter(String filter, Long authUserId) throws CustomAccessDeniedException, NotFoundException {


            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(authUserId);
            RegisteredPlayer peer = registeredPlayerRepository.getReferenceByLoginEmailOrDisplayName(filter.replaceAll("\\s+", "").toLowerCase(), filter);

            boolean isConnection = confirmedConnectionRepository.existsByPeerAAndPeerBOrPeerAAndPeerB(registeredPlayer, peer, peer, registeredPlayer);
            boolean blockExists = blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(registeredPlayer, peer, peer, registeredPlayer);

            if (peer == null || isConnection || blockExists || Objects.equals(registeredPlayer.getId(), peer.getId())) {
                throw new NotFoundException("No registered players available for connection by that filter.");
            }

            else {
                return new RegisteredPlayerResponseDTO(peer.getId(), peer.getFirstName(), peer.getLastName(), peer.getAvatar(), peer.getLoginEmail(), peer.getDisplayName());
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
    public ConnectionRequestResponseDTO createConnectionRequest(Long peerId, Long authUserId) throws CustomAccessDeniedException, ExistingResourceException, NotFoundException {


            if (authUserId.equals(peerId)) {
                throw new NotFoundException("You cannot connect with yourself.");
            }

            else {

                RegisteredPlayer sender = registeredPlayerRepository.getReferenceById(authUserId);
                RegisteredPlayer recipient = registeredPlayerRepository.getReferenceById(peerId);

                if (blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(sender, recipient, recipient, sender)) {
                    throw new CustomAccessDeniedException("You do not have access to this Resource.");
                }

                else {

                    if(connectionRequestRepository.existsBySender_IdAndRecipient_IdOrSender_IdAndRecipient_Id(authUserId, peerId, peerId, authUserId) ) {
                        throw new ExistingResourceException("You already have a pending or accepted connection request with this player.");
                    }

                    else {
                        ConnectionRequest newConnectionRequest = new ConnectionRequest(sender, recipient, ConnectionRequestStatus.PENDING);
                        Long connectionRequestId = connectionRequestRepository.save(newConnectionRequest).getId();

                        return new ConnectionRequestResponseDTO(connectionRequestId, sender.getId(), sender.getFirstName(), sender.getLastName(), sender.getLoginEmail(), recipient.getId(), recipient.getFirstName(), recipient.getLastName(), recipient.getLoginEmail(), ConnectionRequestStatus.PENDING);
                    }
                }
            }


        }


    //</editor-fold>

    //<editor-fold desc = "Confirm Connection">

    @Transactional
    public ConfirmedConnectionResponseDTO confirmConnection(Long connectionRequestId, Long authUserId) throws CustomAccessDeniedException{


            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);

            Long senderId = connectionRequest.getSender().getId();
            Long recipientId = connectionRequest.getRecipient().getId();

            if (authUserId.equals(recipientId)) {

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


    //</editor-fold>

    //<editor-fold desc = "Decline Connection Request">

    @Transactional
    public void declineConnectionRequest(Long connectionRequestId, Long authUserId) throws CustomAccessDeniedException {



            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(connectionRequestId);
            if (authUserId.equals(connectionRequest.getRecipient().getId())) {

                connectionRequestRepository.delete(connectionRequest);

            }

            else {
                throw new CustomAccessDeniedException("You do not have access to this Resource.");
            }

        }


    //</editor-fold>

    //<editor-fold desc = "Block Player">
    @Transactional
    public BlockedRelationshipResponseDTO blockRegisteredPlayer(Long blockedPlayerId, Long authUserId) throws CustomAccessDeniedException {


            // Change existing Requests to BLOCKED
            if (connectionRequestRepository.existsBySender_IdAndRecipient_IdOrSender_IdAndRecipient_Id(authUserId, blockedPlayerId, blockedPlayerId, authUserId)){

                ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceBySender_IdAndRecipient_IdOrSender_IdAndRecipientIdAndConnectionRequestStatus(authUserId, blockedPlayerId, blockedPlayerId, authUserId, ConnectionRequestStatus.ACCEPTED);

                if (connectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.ACCEPTED)) {

                    ConfirmedConnection confirmedConnection = confirmedConnectionRepository.getReferenceByConnectionRequest_Id(connectionRequest.getId());
                    confirmedConnectionRepository.delete(confirmedConnection);

                }

                connectionRequestRepository.delete(connectionRequest);

            }

            // Create Blocked Entity
            RegisteredPlayer blocker = registeredPlayerRepository.getReferenceById(authUserId);
            RegisteredPlayer blocked = registeredPlayerRepository.getReferenceById(blockedPlayerId);
            BlockedRelationshipId blockedRelationshipId = new BlockedRelationshipId(authUserId, blockedPlayerId);
            BlockedRelationship blockedRelationship = new BlockedRelationship(blockedRelationshipId, blocker, blocked);
            blockedRelationshipRepository.save(blockedRelationship);

            return new BlockedRelationshipResponseDTO(authUserId, blockedPlayerId, true);

        }


    //</editor-fold>

    //<editor-fold desc = "Remove Block">

    @Transactional
    public void removeBlock(Long blockedId, Long authUserId) throws CustomAccessDeniedException {


                BlockedRelationshipId blockedRelationshipId = new BlockedRelationshipId(authUserId, blockedId);
                BlockedRelationship blockedRelationship = blockedRelationshipRepository.getReferenceById(blockedRelationshipId);
                blockedRelationshipRepository.delete(blockedRelationship);

        }



    //</editor-fold>

    //<editor-fold desc = " View Sent Connection Requests">

    @Transactional(readOnly = true)
    public Set<ConnectionRequestResponseDTO> getAllSentConnectionRequests(Long authUserId) throws CustomAccessDeniedException {


            Set<ConnectionRequest> allSentConnectionRequests = connectionRequestRepository.getAllBySender_IdAndConnectionRequestStatus(authUserId, ConnectionRequestStatus.PENDING);
            Set<ConnectionRequestResponseDTO> allSentConnectionRequestResponses = new HashSet<>();

            for (ConnectionRequest connectionRequest : allSentConnectionRequests) {
                RegisteredPlayer recipient = registeredPlayerRepository.getReferenceById(connectionRequest.getRecipient().getId());
                RegisteredPlayer sender = registeredPlayerRepository.getReferenceById(connectionRequest.getSender().getId());
                ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(connectionRequest.getId(), sender.getId(), sender.getFirstName(), sender.getLastName(), sender.getLoginEmail(), connectionRequest.getRecipient().getId(), recipient.getFirstName(), recipient.getLastName(), recipient.getLoginEmail(), connectionRequest.getConnectionRequestStatus());
                allSentConnectionRequestResponses.add(connectionRequestResponseDTO);

            }

            return allSentConnectionRequestResponses;

    }

    //</editor-fold>

    //<editor-fold desc = " View Pending Connection Requests">

    @Transactional(readOnly = true)
    public Set<ConnectionRequestResponseDTO> getAllPendingConnectionRequests(Long authUserId) throws CustomAccessDeniedException {


            Set<ConnectionRequest> allPendingConnectionRequests = connectionRequestRepository.getAllByRecipient_IdAndConnectionRequestStatus(authUserId, ConnectionRequestStatus.PENDING);
            Set<ConnectionRequestResponseDTO> allPendingConnectionRequestResponses = new HashSet<>();

            for (ConnectionRequest connectionRequest : allPendingConnectionRequests) {
                RegisteredPlayer recipient = registeredPlayerRepository.getReferenceById(connectionRequest.getRecipient().getId());
                RegisteredPlayer sender = registeredPlayerRepository.getReferenceById(connectionRequest.getSender().getId());
                ConnectionRequestResponseDTO connectionRequestResponseDTO = new ConnectionRequestResponseDTO(connectionRequest.getId(), sender.getId(), sender.getFirstName(), sender.getLastName(), sender.getLoginEmail(), connectionRequest.getRecipient().getId(), recipient.getFirstName(), recipient.getLastName(), recipient.getLoginEmail(), connectionRequest.getConnectionRequestStatus());
                allPendingConnectionRequestResponses.add(connectionRequestResponseDTO);

            }

            return allPendingConnectionRequestResponses;

        }




    //</editor-fold>

    //<editor-fold desc = "Cancel Sent ConnectionRequest">

    @Transactional
    public void cancelConnectionRequest(Long connectionRequestId, Long authUserId) throws CustomAccessDeniedException, NotFoundException {



            ConnectionRequest connectionRequest = connectionRequestRepository.findById(connectionRequestId).orElse(null);
            if(connectionRequest != null) {
                if(connectionRequest.getSender().getId().equals(authUserId) && connectionRequest.getConnectionRequestStatus().equals(ConnectionRequestStatus.PENDING)) {

                    connectionRequestRepository.delete(connectionRequest);

                }

                else {
                    throw new CustomAccessDeniedException("You do not have access to this resource.");
                }
            }
            else {
                throw new NotFoundException("This connection request does not exist.");
            }


        }


    //</editor-fold>

    //<editor-fold desc = "View All Connections">

    @Transactional(readOnly = true)
    public Set<RegisteredPlayerResponseDTO> getAllConnections(Long authUserId) throws CustomAccessDeniedException, NotFoundException {


            Set<ConfirmedConnection> allConnections = confirmedConnectionRepository.getAllByPeerA_Id(authUserId);
            allConnections.addAll(confirmedConnectionRepository.getAllByPeerB_Id(authUserId));

            Set<RegisteredPlayerResponseDTO> allConnectedPlayersResponseDTO = new HashSet<>();

            for (ConfirmedConnection confirmedConnection : allConnections) {

                Long peerAId = confirmedConnection.getPeerA().getId();
                Long peerBId = confirmedConnection.getPeerB().getId();

                if(!peerAId.equals(authUserId)) {
                    RegisteredPlayer connection = registeredPlayerRepository.getReferenceById(peerAId);
                    allConnectedPlayersResponseDTO.add(new RegisteredPlayerResponseDTO(connection.getId(), connection.getFirstName(), connection.getLastName(), connection.getAvatar(), connection.getLoginEmail(), connection.getDisplayName()));
                }
                else {
                    RegisteredPlayer connection = registeredPlayerRepository.getReferenceById(peerBId);
                    allConnectedPlayersResponseDTO.add(new RegisteredPlayerResponseDTO(connection.getId(), connection.getFirstName(), connection.getLastName(), connection.getAvatar(), connection.getLoginEmail(), connection.getDisplayName()));
                }

            }


            return allConnectedPlayersResponseDTO;




        }



    //</editor-fold>

    //<editor-fold desc = "Remove Connection">

    @Transactional
    public void removeConnection(Long peerId, Long authUserId) throws CustomAccessDeniedException {

            Long peerAId ;
            Long peerBId;

            if(authUserId < peerId) {
                peerAId = authUserId;
                peerBId = peerId;
            }
            else {
                peerAId = peerId;
                peerBId = authUserId;
            }

            ConfirmedConnectionId confirmedConnectionId = new ConfirmedConnectionId(peerAId, peerBId);
            ConfirmedConnection confirmedConnection = confirmedConnectionRepository.getReferenceById(confirmedConnectionId);

            ConnectionRequest connectionRequest = connectionRequestRepository.getReferenceById(confirmedConnection.getConnectionRequest().getId());

            confirmedConnectionRepository.delete(confirmedConnection);
            connectionRequestRepository.delete(connectionRequest);

    }



    //</editor-fold>

    //<editor-fold desc = "Get Available GhostPlayers">

    @Transactional(readOnly = true)
    public GhostPlayerResponseDTO getUnassociatedGhostPlayerByEmail(String identifierEmail, Long authUserId) throws CustomAccessDeniedException, NotFoundException{


            GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceByIdentifierEmail(identifierEmail.replaceAll("\\s+", "").toLowerCase());
            boolean isAssociate = registeredPlayerRepository.existsByIdAndAssociations(authUserId, ghostPlayer);

            if (ghostPlayer == null || isAssociate) {
                throw new NotFoundException("No ghost players available for connection by that filter.");
            }

            else {

                return new GhostPlayerResponseDTO(ghostPlayer.getId(), ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());

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
    public GhostPlayerResponseDTO addAssociation(Long ghostPlayerId, Long authUserId) throws CustomAccessDeniedException, NotFoundException {


            Optional <GhostPlayer> ghostPlayer = ghostPlayerRepository.findById(ghostPlayerId);
            if (ghostPlayer.isPresent()) {

                GhostPlayer ghostPlayerExists = ghostPlayer.get();
                boolean isAssociate = registeredPlayerRepository.existsByIdAndAssociations(authUserId, ghostPlayerExists);

                if (!isAssociate) {
                    RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(authUserId);
                    registeredPlayer.getAssociations().add(ghostPlayerExists);

                    return new GhostPlayerResponseDTO(ghostPlayerId, ghostPlayerExists.getFirstName(), ghostPlayerExists.getLastName(), ghostPlayerExists.getAvatar(), ghostPlayerExists.getIdentifierEmail(), ghostPlayerExists.getCreator().getId());

                }
                else {
                    throw new NotFoundException("No ghost players available for connection by that filter.");
                }

            }

            else {
                throw new NotFoundException("No ghost players available for connection by that filter.");
            }


        }


    //</editor-fold>

    //<editor-fold desc = " View All Associations">

    @Transactional(readOnly = true)
    public Set<GhostPlayerResponseDTO> getAllAssociations(Long authUserId) {


            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(authUserId);
            Set<GhostPlayer> allAssociations = registeredPlayer.getAssociations();

            Set<GhostPlayerResponseDTO> allAssociationResponses = new HashSet<>();

            for (GhostPlayer ghostPlayer : allAssociations) {

                GhostPlayerResponseDTO ghostPlayerResponseDTO = new GhostPlayerResponseDTO(ghostPlayer.getId(), ghostPlayer.getFirstName(), ghostPlayer.getLastName(), ghostPlayer.getAvatar(), ghostPlayer.getIdentifierEmail(), ghostPlayer.getCreator().getId());
                allAssociationResponses.add(ghostPlayerResponseDTO);
            }

            return allAssociationResponses;

        }



    //</editor-fold>

    //<editor-fold desc = "Remove Associations">

    @Transactional
    public void removeAssociation(Long ghostPlayerId, Long authUserId) throws CustomAccessDeniedException{


            RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(authUserId);
            GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceById(ghostPlayerId);

            registeredPlayer.getAssociations().remove(ghostPlayer);

        }


    //</editor-fold>



}
