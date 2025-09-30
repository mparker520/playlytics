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

    public BasicAnalyticsResponseDTO getOwnedGameFrequency(Long authUserId, String selectedView, Long selectedGameId) {

        List<String> labels = List.of();
        List<Long> data = List.of();

        if (selectedView.equals("all")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyAll(authUserId);

             labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }


        if (selectedView.equals("topFive")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyTopFive(authUserId);

            labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }



        if (selectedView.equals("bottomFive")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyBottomFive(authUserId);

            labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }

        if (selectedView.equals("byGame")) {
            if (selectedGameId != null) {
                OwnedGameFrequencyProjection row = analyticsRepository.getOwnedGameFrequencyByName(authUserId, selectedGameId);

                labels = List.of(row.getTitle());
                data = List.of(row.getPlayCount());
            }

        }



        return new BasicAnalyticsResponseDTO("Owned Game Play Frequency", labels, data);

    }

    //</editor-fold>

    //<editor-fold desc="Get Play Trends">

    public BasicAnalyticsResponseDTO getPlayTrends(Long authUserId, String selectedGameView, String selectedGranularity, Long selectedStartingYear, Long selectedEndingYear, Long selectedGame1Id, Long selectedGame2Id) {

        List<String> labels = List.of();
        List<Long> data = List.of();

        if (selectedGranularity.equals("month")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getPlayTrendsByGameGranularityMonth(authUserId, selectedGame1Id, selectedGame2Id, selectedStartingYear, selectedEndingYear);

            labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }

        if (selectedGranularity.equals("year")) {
            List<OwnedGameFrequencyProjection> rows = analyticsRepository.getPlayTrendsByGameGranularityYear(authUserId, selectedGame1Id, selectedGame2Id, selectedStartingYear, selectedEndingYear);

            labels = rows.stream().map(r -> r.getTitle()).toList();
            data = rows.stream().map(r -> r.getPlayCount()).toList();

        }



        return new BasicAnalyticsResponseDTO("Owned Game Play Frequency", labels, data);

    }

    //</editor-fold>

}