package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.*;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.NetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NetworkController {

    //<editor-fold desc = "Constructor">

    private final NetworkService networkService;

    public NetworkController(NetworkService networkService) {

        this.networkService = networkService;

    }

    //</editor-fold>

    //<editor-fold desc = "GET Mappings">

    //<editor-fold desc = "GET All Available RegisteredPlayers for Connection by Filter">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/players")
    public ResponseEntity<RegisteredPlayerResponseDTO> discoverPeers(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(value = "filter") String filter){

        RegisteredPlayerResponseDTO availableRegisteredPlayersDTO;
        availableRegisteredPlayersDTO = networkService.getAvailablePeersByFilter(filter, principal.getAuthenticatedUserId());


        return ResponseEntity.ok(availableRegisteredPlayersDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "GET All Available GhostPlayers for Association by Filter">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ghost-players")
    public ResponseEntity<GhostPlayerResponseDTO> discoverGhostPlayers(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(value = "identifierEmail") String identifierEmail){

        GhostPlayerResponseDTO availableGhostPlayers;
        availableGhostPlayers = networkService.getUnassociatedGhostPlayerByEmail(identifierEmail, principal.getAuthenticatedUserId());

        return ResponseEntity.ok(availableGhostPlayers);

    }

    //</editor-fold>

    //<editor-fold desc = "GET All Confirmed Connections">


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/connections")
    public ResponseEntity<Set<RegisteredPlayerResponseDTO>> getAllConnections(
            @AuthenticationPrincipal CustomUserDetails principal) {

        Set<RegisteredPlayerResponseDTO> allConnections = networkService.getAllConnections(principal.getAuthenticatedUserId());
        return ResponseEntity.ok(allConnections);

    }

    //</editor-fold>

    //<editor-fold desc = "GET All Sent ConnectionRequests">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sent-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>>  getSentConnectionRequests(
            @AuthenticationPrincipal CustomUserDetails principal) {

        Set<ConnectionRequestResponseDTO> allSentConnectionRequests = networkService.getAllSentConnectionRequests(principal.getAuthenticatedUserId());
        return ResponseEntity.ok(allSentConnectionRequests);

    }

    //</editor-fold>

    //<editor-fold desc = "GET All Pending ConnectionRequests">
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pending-connection-requests")
    public ResponseEntity<Set<ConnectionRequestResponseDTO>> getPendingConnectionRequests(
            @AuthenticationPrincipal CustomUserDetails principal) {

           Set<ConnectionRequestResponseDTO> allPendingConnectionRequests = networkService.getAllPendingConnectionRequests(principal.getAuthenticatedUserId());
           return ResponseEntity.ok(allPendingConnectionRequests);

    }

    //</editor-fold>

    //<editor-fold desc = "GET All Associations">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/associations")
    public ResponseEntity<Set<GhostPlayerResponseDTO>> getAssociations(
            @AuthenticationPrincipal CustomUserDetails principal) {

        Set<GhostPlayerResponseDTO> allAssociations = networkService.getAllAssociations(principal.getAuthenticatedUserId());
        return ResponseEntity.ok(allAssociations);

    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc = "PATCH mappings">


    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/decline-connection-request/{connectionRequestId}")
    public ResponseEntity<Void> declineConnectionRequest(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("connectionRequestId") Long connectionRequestId
    ) {

        networkService.declineConnectionRequest(connectionRequestId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }


    //</editor-fold>

    //<editor-fold desc = "POST Mappings">


    //<editor-fold desc = "Create Connection Request">
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/connection-request/{peerId}")
    public ResponseEntity <ConnectionRequestResponseDTO> sendConnectionRequest(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("peerId") Long peerId) {

        ConnectionRequestResponseDTO connectionRequestResponseDTO = networkService.createConnectionRequest(peerId, principal.getAuthenticatedUserId());

        return ResponseEntity.ok(connectionRequestResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "Create Confirmed Connection">

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/confirm-connection/{connectionRequestId}")
    public ResponseEntity<ConfirmedConnectionResponseDTO> confirmConnection(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("connectionRequestId") Long connectionRequestId) {

        ConfirmedConnectionResponseDTO confirmedConnectionResponseDTO = networkService.confirmConnection(connectionRequestId, principal.getAuthenticatedUserId());

        return ResponseEntity.ok(confirmedConnectionResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "Create Association">

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/associations/{ghostPlayerId}")
    public ResponseEntity<GhostPlayerResponseDTO> addAssociation(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("ghostPlayerId") Long ghostPlayerId) {

        GhostPlayerResponseDTO ghostPlayerResponseDTO = networkService.addAssociation(ghostPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(ghostPlayerResponseDTO);

    }

    //</editor-fold>



    //<editor-fold desc = "Block Player">

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/block/{blockedPlayerId}")
    public ResponseEntity<BlockedRelationshipResponseDTO> blockRegisteredPlayer(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("blockedPlayerId") Long blockedPlayerId) {

        BlockedRelationshipResponseDTO blockedRelationshipResponseDTO = networkService.blockRegisteredPlayer(blockedPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.ok(blockedRelationshipResponseDTO);

    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc = "DELETE Mappings">

    //<editor-fold desc = "Remove Confirmed Connection">
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/connections/{peerId}")
    public ResponseEntity<Void> removeConnection(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("peerId") Long peerId) {

        networkService.removeConnection(peerId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }

    //</editor-fold>

    //<editor-fold desc = "Remove Association from GhostPlayer">
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/associations/{ghostPlayerId}")
    public ResponseEntity<Void> removeAssociation(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable ("ghostPlayerId") Long ghostPlayerId) {

        networkService.removeAssociation(ghostPlayerId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();
    }

    //</editor-fold>

    //<editor-fold desc = "Remove Block">
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/remove-blocked-relationship/{blockedId}")
    public ResponseEntity<Void> removeBlock(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("blockedId") Long blockedId){

        networkService.removeBlock(blockedId, principal.getAuthenticatedUserId());
        return ResponseEntity.noContent().build();

    }

    //</editor-fold>

    // <editor-fold desc = "Reverse Sent ConnectionRequest">

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/connection-request/{connectionRequestId}")
    public ResponseEntity<Void> cancelConnectionRequests(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("connectionRequestId") Long connectionRequestId) {

        networkService.cancelConnectionRequest(connectionRequestId, principal.getAuthenticatedUserId());

        return ResponseEntity.noContent().build();

    }

    //</editor-fold>

    //</editor-fold>

}
