package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.analytics.*;
import com.mparker.playlytics.enums.ScoringModel;
import com.mparker.playlytics.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import java.time.Month;
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

            // Starting Empty Lists of Labels and Data
            List<String> labels = List.of();
            List<Long> data = List.of();


            //<editor-fold desc="If ALL">

                if (selectedView.equals("all")) {
                    List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyAll(authUserId);

                     labels = rows.stream().map(r -> r.getTitle()).toList();
                    data = rows.stream().map(r -> r.getPlayCount()).toList();

                }

            //</editor-fold>

            //<editor-fold desc="If Top Five Ranked">

                if (selectedView.equals("topFive")) {
                    List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyTopFive(authUserId);

                    labels = rows.stream().map(r -> r.getTitle()).toList();
                    data = rows.stream().map(r -> r.getPlayCount()).toList();

                }


            //</editor-fold>

            //<editor-fold desc="If Bottom Five Ranked">

                if (selectedView.equals("bottomFive")) {

                    List<OwnedGameFrequencyProjection> rows = analyticsRepository.getOwnedGameFrequencyBottomFive(authUserId);

                    labels = rows.stream().map(r -> r.getTitle()).toList();
                    data = rows.stream().map(r -> r.getPlayCount()).toList();

                }


            //</editor-fold>

            //<editor-fold desc="If By Game">

                if (selectedView.equals("byGame")) {

                    if (selectedGameId != null) {
                        OwnedGameFrequencyProjection row = analyticsRepository.getOwnedGameFrequencyByName(authUserId, selectedGameId);

                        labels = List.of(row.getTitle());
                        data = List.of(row.getPlayCount());

                    }

                }


            //</editor-fold>

            return new BasicAnalyticsResponseDTO("Owned Game Play Frequency", labels, data);

        }


    //</editor-fold>

    //<editor-fold desc="Get Play Trends">

    public AdvancedAnalyticsResponseDTO getPlayTrends(Long authUserId,  String selectedGranularity, Long selectedBaseYear, Long selectedGame1Id, Long selectedGame2Id) {

        // Starting Empty Lists of Labels and Data
        List<PlayTrendProjection> rows;
        List<String> labels =  new ArrayList<>();

        // Hash Map for Datasets
        Map<String, List<Integer>> dataSets = new HashMap<>();

        // Set Starting and Ending Years based on Base year
        Long selectedStartingYear = selectedBaseYear - 1;
        Long selectedEndingYear  = selectedBaseYear +1;


        //<editor-fold desc="If Month Granularity">

            if (selectedGranularity.equals("month")) {

                // Get Rows from Repository
                rows = analyticsRepository.getPlayTrendsByGameGranularityMonth(authUserId, selectedGame1Id, selectedGame2Id, selectedStartingYear, selectedEndingYear);

                //<editor-fold desc="Create Labels from Years and Months">

                int y;

                for(y = selectedStartingYear.intValue(); y <= selectedEndingYear.intValue(); y++) {
                    for (Month m : Month.values()) {
                        String monthName = m.name().substring(0, 1) + m.name().substring(1).toLowerCase();
                        labels.add(monthName + "-" + y);
                    }
                }


                //</editor-fold>

                //<editor-fold desc="Create Label Index Map">

                    Map<String, Integer> labelIndexMap = new HashMap<>();
                    for (int i = 0; i < labels.size(); i++) {
                        labelIndexMap.put(labels.get(i), i);
                    }


                //</editor-fold>

                //<editor-fold desc="Create an array for each game based on size of labels">

                    List<String> gameNames = rows.stream().map(r -> (r.getTitle())).toList();

                    for(String gameName : gameNames) {
                        dataSets.put(gameName, new ArrayList<>(Collections.nCopies(labels.size(), 0)));
                    }


                //</editor-fold>

                //<editor-fold desc="Build Play Trend Projections">

                    for (PlayTrendProjection row : rows) {
                        int monthNum = Integer.parseInt(row.getMonthPlayed());
                        String monthName = Month.of(monthNum).getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
                        int index = labelIndexMap.get(monthName + "-" + row.getYearPlayed());
                        List<Integer> gameData = dataSets.get(row.getTitle());
                        gameData.set(index, row.getPlayCount().intValue());
                    }


                //</editor-fold>

            }


        //</editor-fold>

        //<editor-fold desc="If year Granularity">

            if (selectedGranularity.equals("year")) {

                // Get Rows from Repository
                rows = analyticsRepository.getPlayTrendsByGameGranularityYear(authUserId, selectedGame1Id, selectedGame2Id, selectedStartingYear, selectedEndingYear);


                //<editor-fold desc="Create Labels from Years">

                    Long y;

                    for(y = selectedStartingYear; y <= selectedEndingYear; y++) {
                            labels.add(Long.toString(y));
                    }


                //</editor-fold>

                //<editor-fold desc="Create Label Index Map">

                    Map<String, Integer> labelIndexMap = new HashMap<>();
                    for (int i = 0; i < labels.size(); i++) {
                        labelIndexMap.put(labels.get(i), i);
                    }


                //</editor-fold>

                //<editor-fold desc="Create an array for each game based on size of labels">

                    List<String> gameNames = rows.stream().map(r -> (r.getTitle())).toList();

                    for(String gameName : gameNames) {
                        dataSets.put(gameName, new ArrayList<>(Collections.nCopies(labels.size(), 0)));
                    }


                //</editor-fold>

                //<editor-fold desc="Build Play Trend Projections">

                    for (PlayTrendProjection row : rows) {
                        int index = labelIndexMap.get(row.getYearPlayed());
                        List<Integer> gameData = dataSets.get(row.getTitle());
                        gameData.set(index, row.getPlayCount().intValue());
                    }


                //</editor-fold>

            }


        //</editor-fold>

        return new AdvancedAnalyticsResponseDTO("Play Trends", labels, dataSets);

    }


    //</editor-fold>

}