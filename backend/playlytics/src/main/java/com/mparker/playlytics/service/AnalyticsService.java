package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.analytics.OwnedGameFrequencyProjection;
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.dto.analytics.BasicAnalyticsResponseDTO;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.repository.AnalyticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public BasicAnalyticsResponseDTO getWinLossRatio(Long authUserId, Long selectedGame, ScoringModel selectedScoringModel) {

        WinLossProjection result = analyticsRepository.getWinLossRatio(authUserId, selectedGame, selectedScoringModel);

        return new BasicAnalyticsResponseDTO("Win/Loss Ratio", List.of("Wins", "Losses"), List.of(result.getWins(), result.getLosses()));

    }


//</editor-fold>


    //<editor-fold desc="Get Owned Game Frequency">

    public BasicAnalyticsResponseDTO getOwnedGameFrequency(Long authUserId) {

        List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequency(authUserId);

        List<String> labels = rows.stream().map(r -> r.getTitle()).toList();
        List<Long> data = rows.stream().map(r -> r.getPlayCount()).toList();



        return new BasicAnalyticsResponseDTO("Owned Game Play Frequency", labels, data);

    }

    //</editor-fold>

}