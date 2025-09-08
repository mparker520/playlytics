package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.entity.ConfirmedConnection;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConfirmedConnectionRepository extends JpaRepository<ConfirmedConnection, Long> {
}
