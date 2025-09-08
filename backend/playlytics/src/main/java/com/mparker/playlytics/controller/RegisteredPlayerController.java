package com.mparker.playlytics.controller;

// Imports

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


    //<editor-fold desc = "Update Mapping">

    //</editor-fold>

    //<editor-fold desc = "Delete Mapping">

    @DeleteMapping("registered-players/{registeredPlayerId}")
    public ResponseEntity<Void> deleteRegisteredPlayer(
            @PathVariable("registeredPlayerId") Long registeredPlayerId) {

            registeredPlayerService.deleteRegisteredPlayer(registeredPlayerId);
            return ResponseEntity.noContent().build();

    }

    //</editor-fold>


}
