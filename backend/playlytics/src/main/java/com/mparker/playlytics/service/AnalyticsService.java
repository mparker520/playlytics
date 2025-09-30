package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.analytics.*;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;

import java.util.*;


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

    public AdvancedAnalyticsResponseDTO getPlayTrends(Long authUserId, String selectedGameView, String selectedGranularity, Long selectedStartingYear, Long selectedEndingYear, Long selectedGame1Id, Long selectedGame2Id) {

        List<PlayTrendProjection> rows = List.of();
        List<String> labels = List.of();
        Map<String, Integer> labelIndexMap = new HashMap<>();

        Map<String, List<Integer>> dataSets = new HashMap<>();


        if (selectedGranularity.equals("month")) {
            rows = analyticsRepository.getPlayTrendsByGameGranularityMonth(authUserId, selectedGame1Id, selectedGame2Id, selectedStartingYear, selectedEndingYear);
            labels = rows.stream().map(r -> (r.getMonthPlayed() + "-" + r.getYearPlayed())).toList();

            for (int i = 0; i < labels.size(); i++) {
                labelIndexMap.put(labels.get(i), i);
            }

            List<String> gameNames = rows.stream().map(r -> (r.getTitle())).toList();

            for(String gameName : gameNames) {
                dataSets.put(gameName, new ArrayList<>(Collections.nCopies(labels.size(), 0)));
            }

            for (PlayTrendProjection row : rows) {
                int index = labelIndexMap.get(row.getMonthPlayed() + "-" + row.getYearPlayed());
                List<Integer> gameData = dataSets.get(row.getTitle());
                gameData.set(index, row.getPlayCount().intValue());
            }



        }

        if (selectedGranularity.equals("year")) {
            rows = analyticsRepository.getPlayTrendsByGameGranularityYear(authUserId, selectedGame1Id, selectedGame2Id, selectedStartingYear, selectedEndingYear);
            labels = rows.stream().map(r -> (r.getYearPlayed())).toList();

            for (int i = 0; i < labels.size(); i++) {
                labelIndexMap.put(labels.get(i), i);
            }

            List<String> gameNames = rows.stream().map(r -> (r.getTitle())).toList();

            for(String gameName : gameNames) {
                dataSets.put(gameName, new ArrayList<>(Collections.nCopies(labels.size(), 0)));
            }

            for (PlayTrendProjection row : rows) {
                int index = labelIndexMap.get(row.getYearPlayed());
                List<Integer> gameData = dataSets.get(row.getTitle());
                gameData.set(index, row.getPlayCount().intValue());
            }


        }


        return new AdvancedAnalyticsResponseDTO("Play Trends", labels, dataSets);

    }

    //</editor-fold>

}