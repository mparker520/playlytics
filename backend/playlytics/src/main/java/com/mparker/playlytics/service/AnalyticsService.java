package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.GameResponseDTO;
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.dto.analytics.WinLossResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.repository.AnalyticsRepository;
import com.mparker.playlytics.repository.GameRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service

public class AnalyticsService {

    //<editor-fold desc = "Constructor">

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {

        this.analyticsRepository = analyticsRepository;
}

//</editor-fold>


//<editor-fold desc = "Get Win/Loss Ratio">

    public WinLossResponseDTO getWinLossRatio(Long authUserId) {

        WinLossProjection result = analyticsRepository.getWinLossRatio(authUserId);

        return new WinLossResponseDTO("Win/Loss Ratio", List.of("Wins", "Losses"), List.of(result.getWins(), result.getLosses()));

    }

}

//</editor-fold>

