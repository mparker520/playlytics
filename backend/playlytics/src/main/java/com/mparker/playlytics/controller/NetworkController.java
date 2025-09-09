package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.service.GameInventoryService;
import com.mparker.playlytics.service.NetworkService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    // GET SENT CONNECTION REQUESTS

    // GET PENDING CONNECTION REQUESTS


    //</editor-fold>


    //<editor-fold desc = "POST Mappings">


    // Create Association

    // Create ConnectionRequestion

    // Confirm ConnectionRequestion / Create Confirmed Connection


    //</editor-fold>


    //<editor-fold desc = "DELETE Mappings">


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
