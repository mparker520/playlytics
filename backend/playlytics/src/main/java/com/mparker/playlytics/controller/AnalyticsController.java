package com.mparker.playlytics.controller;

// Imports

import com.mparker.playlytics.dto.analytics.WinLossResponseDTO;
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

    //<editor-fold desc = "GET Mapping">

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/win-loss-ratio")
    public ResponseEntity<WinLossResponseDTO> getWinLossRatio(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(required = false) Long selectedGame,
            @RequestParam(required = false) ScoringModel selectedScoringModel)  {


            WinLossResponseDTO winLossResponseDTO = analyticsService.getWinLossRatio(principal.getAuthenticatedUserId(), selectedGame, selectedScoringModel);
            return ResponseEntity.ok(winLossResponseDTO);

    }

    //</editor-fold>


    //</editor-fold>

}
