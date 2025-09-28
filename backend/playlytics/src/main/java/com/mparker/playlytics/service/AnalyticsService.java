package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.dto.analytics.WinLossResponseDTO;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class AnalyticsService {

    //<editor-fold desc = "Constructor">

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {

        this.analyticsRepository = analyticsRepository;
}

//</editor-fold>


//<editor-fold desc = "Get Win/Loss Ratio">

    public WinLossResponseDTO getWinLossRatio(Long authUserId, Long selectedGame, ScoringModel selectedScoringModel) {

        WinLossProjection result = analyticsRepository.getWinLossRatio(authUserId, selectedGame, selectedScoringModel);

        return new WinLossResponseDTO("Win/Loss Ratio", List.of("Wins", "Losses"), List.of(result.getWins(), result.getLosses()));

    }

}

//</editor-fold>

