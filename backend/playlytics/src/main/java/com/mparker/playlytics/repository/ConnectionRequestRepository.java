package com.mparker.playlytics.repository;

// Imports

import com.mparker.playlytics.dto.ConnectionRequestResponseDTO;
import com.mparker.playlytics.entity.ConnectionRequest;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    Set<ConnectionRequest> getAllBySender_IdAndConnectionRequestStatus(Long registeredPlayerId, ConnectionRequestStatus status);

    Set<ConnectionRequest> getAllByRecipient_IdAndConnectionRequestStatus(Long registeredPlayerId, ConnectionRequestStatus connectionRequestStatus);

    boolean existsBySender_IdAndRecipient_Id(Long registeredPlayerId, Long peerId);

    ConnectionRequest getReferenceBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(Long registeredPlayerId, Long peerId, Long peerId1, Long registeredPlayerId1);
}
