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

    public BasicAnalyticsResponseDTO getOwnedGameFrequency(Long authUserId, String selectedView) {

        List<String> labels = List.of();
        List<Long> data = List.of();

        if (selectedView.equals("all")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyAll(authUserId);

             labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }


        if (selectedView.equals("topTen")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyTopTen(authUserId);

            labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }



        if (selectedView.equals("bottomTen")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyBottomTen(authUserId);

            labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }



        return new BasicAnalyticsResponseDTO("Owned Game Play Frequency", labels, data);

    }

    //</editor-fold>

}