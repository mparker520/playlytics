package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.entity.ConfirmedConnection;
import com.mparker.playlytics.entity.ConfirmedConnectionId;
import com.mparker.playlytics.entity.Player;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;


public interface ConfirmedConnectionRepository extends JpaRepository<ConfirmedConnection, ConfirmedConnectionId> {

    // Get all confirmed Connections by Peer A, Where Peer A is User
    Set<ConfirmedConnection> getAllByPeerA_Id(Long registeredPlayerId);

    // Get all confirmed Connections by Peer B, Where Peer B is User
    Collection<? extends ConfirmedConnection> getAllByPeerB_Id(Long registeredPlayerId);

    // Check if connection exists by Peer A and Peer B or Peer B and Peer A
    boolean existsByPeerAAndPeerBOrPeerAAndPeerB(RegisteredPlayer registeredPlayer, Player peer, Player peer1, RegisteredPlayer registeredPlayer1);

}
