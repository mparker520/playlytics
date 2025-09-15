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
    Set<ConfirmedConnection> getAllByPeerA_Id(Long registeredPlayerId);

    Collection<? extends ConfirmedConnection> getAllByPeerB_Id(Long registeredPlayerId);

    ConfirmedConnection getReferenceByConnectionRequest_Id(Long id);

    boolean existsByPeerAAndPeerBOrPeerAAndPeerB(RegisteredPlayer registeredPlayer, Player peer, Player peer1, RegisteredPlayer registeredPlayer1);

}
