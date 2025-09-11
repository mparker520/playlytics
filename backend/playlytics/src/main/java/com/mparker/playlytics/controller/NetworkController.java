package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.ConfirmedConnectionResponseDTO;
import com.mparker.playlytics.dto.ConnectionRequestResponseDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.service.NetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController

public class NetworkController {

    //<editor-fold desc = "Constructor">

    private final NetworkService networkService;

    public NetworkController(NetworkService networkService) {

        this.networkService = networkService;

    }

    //</editor-fold>


    //<editor-fold desc = "GET Mappings">

    // TODO:
    // Discover Networking Opportunities


    // GET CONFIRMED CONNECTIONS

    @GetMapping("/network/{registeredPlayerId}/connections")
    public ResponseEntity<Set<ConfirmedConnectionResponseDTO>> getAllConnections(
            @PathVariable("registeredPlayerId") Long registeredPlayerId) {

        Set<ConfirmedConnectionResponseDTO> allConnections = networkService.getAllConnections(registeredPlayerId);
        return ResponseEntity.ok(allConnections);

    }


    // GET SENT CONNECTION REQUESTS

    @GetMapping("/network/{registeredPlayerId}/sent-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>>  getSentConnectionRequests(
            @PathVariable("registeredPlayerId") Long registeredPlayer) {

        Set<ConnectionRequestResponseDTO> allSentConnectionRequests = networkService.getAllSentConnectionRequests(registeredPlayer);
        return ResponseEntity.ok(allSentConnectionRequests);

    }

    // GET PENDING CONNECTION REQUESTS

    @GetMapping("network/{registeredPlayerId}/pending-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>> getPendingConnectionRequests(
            @PathVariable("registeredPlayerId") Long registeredPlayerId) {

           Set<ConnectionRequestResponseDTO> allPendingConnectionRequests = networkService.getAllPendingConnectionRequests(registeredPlayerId);
           return ResponseEntity.ok(allPendingConnectionRequests);

    }


    // GET ASSOCIATIONS

    @GetMapping("/network/{registeredPlayerId}/associations")
    public ResponseEntity<Set<GhostPlayerResponseDTO>> getAssociations(
            @PathVariable("registeredPlayerId") Long registeredPlayerId) {

        Set<GhostPlayerResponseDTO> allAssociations = networkService.getAllAssociations(registeredPlayerId);
        return ResponseEntity.ok(allAssociations);

    }


    //</editor-fold>


    //<editor-fold desc = "POST Mappings">


    //  Create ConnectionRequest
    @PostMapping("/network/{registeredPlayerId}/send-connection-request/{peerId}")
    public ResponseEntity<ConnectionRequestResponseDTO> sendConnectionRequest(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("peerId") Long peerId) {

        ConnectionRequestResponseDTO connectionRequestResponseDTO = networkService.createConnectionRequest(registeredPlayerId, peerId);

        return ResponseEntity.ok(connectionRequestResponseDTO);

    }


    //  Confirm ConnectionRequest / Create Confirmed Connection
    @PostMapping("/network/{registeredPlayerId}/confirm-connection/{connectionRequestId}")
    public ResponseEntity<Optional<ConfirmedConnectionResponseDTO>> confirmConnection(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("connectionRequestId") Long connectionRequestId) {

        Optional<ConfirmedConnectionResponseDTO> confirmedConnectionResponseDTO = networkService.confirmConnection(registeredPlayerId, connectionRequestId);

        return ResponseEntity.ok(confirmedConnectionResponseDTO);

    }



    // Create Association

    @PostMapping("/network/{registeredPlayerId}/add-association/{ghostPlayerId}")
    public ResponseEntity<GhostPlayerResponseDTO> addAssociation(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("ghostPlayerId") Long ghostPlayerId) {

        GhostPlayerResponseDTO ghostPlayerResponseDTO = networkService.addAssociation(registeredPlayerId, ghostPlayerId);
        return ResponseEntity.ok(ghostPlayerResponseDTO);

    }

    //</editor-fold>


    //<editor-fold desc = "DELETE Mappings">
    // Remove Confirmed Connection

    @DeleteMapping("/network/{registeredPlayerId}/remove-connection/{peerAId}/{peerBId}")
    public ResponseEntity<Void> removeConnection(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("peerAId") Long peerAId,
            @PathVariable("peerBId") Long peerBId) {

        networkService.removeConnection(registeredPlayerId, peerAId, peerBId);
        return ResponseEntity.noContent().build();

    }

    // Remove Association with GhostPlayer

    @DeleteMapping("/network/{registeredPlayerId}/remove-association/{ghostPlayerId}")
    public ResponseEntity<Void> removeAssociation(
            @PathVariable ("registeredPlayerId") Long registeredPlayerId,
            @PathVariable ("ghostPlayerId") Long ghostPlayerId) {

        networkService.removeAssociation(registeredPlayerId, ghostPlayerId);
        return ResponseEntity.noContent().build();
    }


    //  TODO: Remove Confirmed Connection / Dissolve Connection Request


    // Delete Sent ConnectionRequest

    @DeleteMapping("/network/{registeredPlayerId}/cancel-connection-requests/{connectionRequestId}")
    public ResponseEntity<Void> removePendingConnectionRequests(
            @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("connectionRequestId") Long connectionRequestId) {

        networkService.cancelConnectionRequest(registeredPlayerId, connectionRequestId);

        return ResponseEntity.noContent().build();

    }

    //</editor-fold>

}
