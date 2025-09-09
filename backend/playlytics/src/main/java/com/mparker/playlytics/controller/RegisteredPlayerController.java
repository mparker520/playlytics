package com.mparker.playlytics.controller;

// Imports

import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.dto.RegisteredPlayerResponseDTO;
import com.mparker.playlytics.dto.RegisteredPlayerUpdateDTO;
import com.mparker.playlytics.service.RegisteredPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

public class RegisteredPlayerController {

    //<editor-fold desc = "Constructor">

    private final RegisteredPlayerService registeredPlayerService;

    public RegisteredPlayerController(RegisteredPlayerService registeredPlayerService) {
        this.registeredPlayerService = registeredPlayerService;
    }

    //</editor-fold>


    //<editor-fold desc = "PATCH Mapping">

    @PatchMapping("/registered-players/{registeredPlayerId}")
    public ResponseEntity<RegisteredPlayerUpdateDTO> updateGhostPlayer(
            @PathVariable Long registeredPlayerId,
            @RequestBody RegisteredPlayerUpdateDTO registeredPlayerUpdateDTO) {

        RegisteredPlayerUpdateDTO registeredPlayerResponseDTO = registeredPlayerService.updateRegisteredPlayer(registeredPlayerId, registeredPlayerUpdateDTO);
        return ResponseEntity.ok(registeredPlayerResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "DELETE Mapping">

    @DeleteMapping("registered-players/{registeredPlayerId}")
    public ResponseEntity<Void> deleteRegisteredPlayer(
            @PathVariable("registeredPlayerId") Long registeredPlayerId) {

            registeredPlayerService.deleteRegisteredPlayer(registeredPlayerId);
            return ResponseEntity.noContent().build();

    }

    //</editor-fold>


}
