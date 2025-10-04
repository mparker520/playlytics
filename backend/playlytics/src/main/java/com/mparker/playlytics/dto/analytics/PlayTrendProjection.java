package com.mparker.playlytics.dto.analytics;

public interface PlayTrendProjection {

        String getYearPlayed();
        String getMonthPlayed();
        String getTitle();
        Long getPlayCount();

}
