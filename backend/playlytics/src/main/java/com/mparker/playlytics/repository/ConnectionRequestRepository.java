package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.entity.ConnectionRequest;
import com.mparker.playlytics.entity.RegisteredPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    ConnectionRequest getReferenceBySenderIdAndRecipientId(RegisteredPlayer registeredPlayerId, RegisteredPlayer peerId);

}
