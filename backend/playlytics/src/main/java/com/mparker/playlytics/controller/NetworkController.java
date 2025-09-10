package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.ConnectionRequestResponseDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.entity.ConnectionRequest;
import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.repository.ConnectionRequestRepository;
import com.mparker.playlytics.service.GameInventoryService;
import com.mparker.playlytics.service.NetworkService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    // Discover Networking Opportunities

    // GET CONFIRMED CONNECTIONS

    // GET ASSOCIATIONS

    @GetMapping("/network/{registeredPlayerId}/associations")
    public ResponseEntity<Set<GhostPlayerResponseDTO>> getAssociations(
            @PathVariable("registeredPlayerId") Long registeredPlayerId) {

        Set<GhostPlayerResponseDTO> allAssociations = networkService.getAllAssociations(registeredPlayerId);
        return ResponseEntity.ok(allAssociations);

    }

    // GET SENT CONNECTION REQUESTS

    @GetMapping("/network/{registeredPlayerId}/sent-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>>  getSentConnectionRequests(
            @PathVariable("registeredPlayerId") Long registeredPlayer) {

        Set<ConnectionRequestResponseDTO> allSentConnectionRequests = networkService.getAllSentConnectionRequests(registeredPlayer);
        return ResponseEntity.ok(allSentConnectionRequests);

    }

    // GET PENDING CONNECTION REQUESTS


    //</editor-fold>


    //<editor-fold desc = "POST Mappings">


    // Create Association

    // Create ConnectionRequestion

    // Confirm ConnectionRequestion / Create Confirmed Connection


    //</editor-fold>


    //<editor-fold desc = "DELETE Mappings">
    // TODO: Remove Confirmed Connection

    // Remove Association with GhostPlayer

    @DeleteMapping("/network/{registeredPlayerId}/remove-associations/{ghostPlayerId}")
    public ResponseEntity<Void> removeAssociation(
            @PathVariable ("registeredPlayerId") Long registeredPlayerId,
            @PathVariable ("ghostPlayerId") Long ghostPlayerId) {

        networkService.removeAssociation(registeredPlayerId, ghostPlayerId);
        return ResponseEntity.noContent().build();
    }


    // Remove Confirmed Connection / Dissolve Connection Request


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
