package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.entity.ConfirmedConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;


public interface ConfirmedConnectionRepository extends JpaRepository<ConfirmedConnection, Long> {
    Set<ConfirmedConnection> getAllByPeerA_Id(Long registeredPlayerId);

    Collection<? extends ConfirmedConnection> getAllByPeerB_Id(Long registeredPlayerId);

}
