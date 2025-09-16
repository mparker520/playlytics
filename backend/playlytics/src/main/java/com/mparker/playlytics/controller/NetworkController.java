package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.*;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.NetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
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

    // Discover RegisteredPlayers
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @GetMapping("/network/{registeredPlayerId}/discover-players")
    public ResponseEntity<RegisteredPlayerResponseDTO> discoverPeers(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @RequestParam(value = "filter") String filter){

        RegisteredPlayerResponseDTO availableRegisteredPlayersDTO;
        availableRegisteredPlayersDTO = networkService.getAvailablePeersByFilter(registeredPlayerId, filter, principal.getAuthenticatedUserId());


        return ResponseEntity.ok(availableRegisteredPlayersDTO);

    }



    // Discover GhostPlayers
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @GetMapping("/network/{registeredPlayerId}/discover-ghost-players")
    public ResponseEntity<GhostPlayerResponseDTO> discoverUnassociatedGhostPlayers(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @RequestParam(value = "identifierEmail") String identifierEmail){

        GhostPlayerResponseDTO availableGhostPlayers;
        availableGhostPlayers = networkService.getUnassociatedGhostPlayerByEmail(registeredPlayerId, identifierEmail, principal.getAuthenticatedUserId());

        return ResponseEntity.ok(availableGhostPlayers);

    }



    // GET CONFIRMED CONNECTIONS
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @GetMapping("/network/{registeredPlayerId}/connections")
    public ResponseEntity<Set<ConfirmedConnectionResponseDTO>> getAllConnections(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId) {

        Set<ConfirmedConnectionResponseDTO> allConnections = networkService.getAllConnections(registeredPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(allConnections);

    }


    // GET SENT CONNECTION REQUESTS
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @GetMapping("/network/{registeredPlayerId}/sent-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>>  getSentConnectionRequests(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayer) {

        Set<ConnectionRequestResponseDTO> allSentConnectionRequests = networkService.getAllSentConnectionRequests(registeredPlayer, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(allSentConnectionRequests);

    }

    // GET PENDING CONNECTION REQUESTS
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @GetMapping("network/{registeredPlayerId}/pending-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>> getPendingConnectionRequests(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId) {

           Set<ConnectionRequestResponseDTO> allPendingConnectionRequests = networkService.getAllPendingConnectionRequests(registeredPlayerId, principal.getAuthenticatedUserId());
           return ResponseEntity.ok(allPendingConnectionRequests);

    }


    // GET ASSOCIATIONS
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @GetMapping("/network/{registeredPlayerId}/associations")
    public ResponseEntity<Set<GhostPlayerResponseDTO>> getAssociations(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId) {

        Set<GhostPlayerResponseDTO> allAssociations = networkService.getAllAssociations(registeredPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(allAssociations);

    }


    //</editor-fold>

    //<editor-fold desc = "PATCH mappings">


    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @PatchMapping("/network/{registeredPlayerId}/decline-connection-request/{connectionRequestId}")
    public ResponseEntity<Void> declineConnectionRequest(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("connectionRequestId") Long connectionRequestId
    ) {

        networkService.declineConnectionRequest(registeredPlayerId, connectionRequestId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }




    //</editor-fold>

    //<editor-fold desc = "POST Mappings">


    //  Create ConnectionRequest
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @PostMapping("/network/{registeredPlayerId}/send-connection-request/{peerId}")
    public ResponseEntity <ConnectionRequestResponseDTO> sendConnectionRequest(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("peerId") Long peerId) {

        ConnectionRequestResponseDTO connectionRequestResponseDTO = networkService.createConnectionRequest(registeredPlayerId, peerId, principal.getAuthenticatedUserId());

        return ResponseEntity.ok(connectionRequestResponseDTO);

    }


    //  Confirm ConnectionRequest / Create Confirmed Connection
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @PostMapping("/network/{registeredPlayerId}/confirm-connection/{connectionRequestId}")
    public ResponseEntity<ConfirmedConnectionResponseDTO> confirmConnection(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("connectionRequestId") Long connectionRequestId) {

        ConfirmedConnectionResponseDTO confirmedConnectionResponseDTO = networkService.confirmConnection(registeredPlayerId, connectionRequestId, principal.getAuthenticatedUserId());

        return ResponseEntity.ok(confirmedConnectionResponseDTO);

    }


    // Create Association
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @PostMapping("/network/{registeredPlayerId}/add-association/{ghostPlayerId}")
    public ResponseEntity<GhostPlayerResponseDTO> addAssociation(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("ghostPlayerId") Long ghostPlayerId) {

        GhostPlayerResponseDTO ghostPlayerResponseDTO = networkService.addAssociation(registeredPlayerId, ghostPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(ghostPlayerResponseDTO);

    }

    // REVERSE Sent ConnectionRequest
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @PatchMapping("/network/{registeredPlayerId}/cancel-connection-requests/{connectionRequestId}")
    public ResponseEntity<Void> removePendingConnectionRequests(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("connectionRequestId") Long connectionRequestId) {

        networkService.cancelConnectionRequest(registeredPlayerId, connectionRequestId, principal.getAuthenticatedUserId());

        return ResponseEntity.noContent().build();

    }

    // BLOCK Player
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @PostMapping("/network/{registeredPlayerId}/block-registered-player/{blockedPlayerId}")
    public ResponseEntity<BlockedRelationshipResponseDTO> blockRegisteredPlayer(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("blockedPlayerId") Long blockedPlayerId) {

        BlockedRelationshipResponseDTO blockedRelationshipResponseDTO = networkService.blockRegisteredPlayer(registeredPlayerId, blockedPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(blockedRelationshipResponseDTO);

    }


    //</editor-fold>

    //<editor-fold desc = "DELETE Mappings">

    // Remove Confirmed Connection
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @DeleteMapping("/network/{registeredPlayerId}/remove-connection/{peerAId}/{peerBId}")
    public ResponseEntity<Void> removeConnection(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("peerAId") Long peerAId,
            @PathVariable("peerBId") Long peerBId) {

        networkService.removeConnection(registeredPlayerId, peerAId, peerBId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }

    // Remove Association with GhostPlayer
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @DeleteMapping("/network/{registeredPlayerId}/remove-association/{ghostPlayerId}")
    public ResponseEntity<Void> removeAssociation(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable ("registeredPlayerId") Long registeredPlayerId,
            @PathVariable ("ghostPlayerId") Long ghostPlayerId) {

        networkService.removeAssociation(registeredPlayerId, ghostPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();
    }



    // Remove Block
    @PreAuthorize("#registeredPlayerId == principal.authenticatedUserId")
    @DeleteMapping("/network/{registeredPlayerId}/remove-blocked-relationship/{blockerId}/{blockedId}")
    public ResponseEntity<Void> removeBlock(
            @AuthenticationPrincipal CustomUserDetails principal,
            @P("registeredPlayerId") @PathVariable("registeredPlayerId") Long registeredPlayerId,
            @PathVariable("blockerId") Long blockerId,
            @PathVariable("blockedId") Long blockedId){

        networkService.removeBlock(registeredPlayerId, blockerId, blockedId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }


    //</editor-fold>

}
