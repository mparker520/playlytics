package com.mparker.playlytics.controller;

// Imports
import com.mparker.playlytics.dto.GhostPlayerDTO;
import com.mparker.playlytics.dto.GhostPlayerResponseDTO;
import com.mparker.playlytics.service.GhostPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class GhostPlayerController {

    //<editor-fold desc = "Constructor">

    private final GhostPlayerService ghostPlayerService;

    public GhostPlayerController(GhostPlayerService ghostPlayerService) {
        this.ghostPlayerService = ghostPlayerService;
    }

    //</editor-fold>

    //<editor-fold desc = "GET Mapping">
    //</editor-fold>

    //<editor-fold desc = "PATCH Mapping">
    //</editor-fold>

    //<editor-fold desc = "POST Mapping">
    @PostMapping("/ghost-players")
    public ResponseEntity<GhostPlayerResponseDTO> createGhostPlayer(
            @RequestBody GhostPlayerDTO ghostPlayerDTO) {

        GhostPlayerResponseDTO ghostPlayerResponseDTO = ghostPlayerService.createNewGhostPlayer(ghostPlayerDTO);
        return ResponseEntity.ok(ghostPlayerResponseDTO);

    }
    //</editor-fold>


}
