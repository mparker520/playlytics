package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.dto.analytics.WinLossProjection;
import com.mparker.playlytics.entity.GhostPlayer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface AnalyticsRepository extends JpaRepository<GhostPlayer, Long> {


    @Query (("""
            SELECT 
                SUM(CASE WHEN sp.result = 1 THEN 1 ELSE 0 END),
                SUM(CASE WHEN sp.result <> 1 THEN 1 ELSE 0 END)
            FROM SessionParticipant sp
            WHERE sp.player.id = :playerId
            
            """))
    WinLossProjection getWinLossRatio(@Param ("playerId") Long authUserId);

}
