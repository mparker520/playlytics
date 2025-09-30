package com.mparker.playlytics.controller;

// Imports

import com.mparker.playlytics.dto.analytics.BasicAnalyticsResponseDTO;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.security.CustomUserDetails;
import com.mparker.playlytics.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class AnalyticsController {

    //<editor-fold desc = "Constructor">

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    //</editor-fold>

    //<editor-fold desc = "Win / Loss Ratio">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/win-loss-ratio")
    public ResponseEntity<BasicAnalyticsResponseDTO> getWinLossRatio(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(required = false) Long selectedGame,
            @RequestParam(required = false) ScoringModel selectedScoringModel)  {


            BasicAnalyticsResponseDTO winLossResponseDTO = analyticsService.getWinLossRatio(principal.getAuthenticatedUserId(), selectedGame, selectedScoringModel);
            return ResponseEntity.ok(winLossResponseDTO);

    }

    //</editor-fold>

    //<editor-fold desc = "Owned Game Frequency">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/owned-game-frequency")
    public ResponseEntity<BasicAnalyticsResponseDTO> getOwnedGameFrequency(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(required = true) String selectedView,
            @RequestParam(required = false) Long selectedGameId)  {


        BasicAnalyticsResponseDTO ownedGameFrequencyResponseDTO = analyticsService.getOwnedGameFrequency(principal.getAuthenticatedUserId(), selectedView, selectedGameId);
        return ResponseEntity.ok(ownedGameFrequencyResponseDTO);

    }

    //</editor-fold>


    //</editor-fold>

}
