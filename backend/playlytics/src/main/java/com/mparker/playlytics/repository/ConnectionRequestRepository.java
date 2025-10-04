package com.mparker.playlytics.repository;

// Imports
import com.mparker.playlytics.entity.ConnectionRequest;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.enums.ConnectionRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;


@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    //  Get all connection requests by status for user (SENT)
    Set<ConnectionRequest> getAllBySender_IdAndConnectionRequestStatus(Long registeredPlayerId, ConnectionRequestStatus status);

    // Get all connection requests by status for user (PENDING User acceptance)
    Set<ConnectionRequest> getAllByRecipient_IdAndConnectionRequestStatus(Long registeredPlayerId, ConnectionRequestStatus connectionRequestStatus);

    //  Get connection request reference for user and peer or peer and user
    ConnectionRequest getReferenceBySender_IdAndRecipient_IdOrSender_IdAndRecipientId(Long registeredPlayerId, Long peerId, Long peerId1, Long registeredPlayerId1);

    // Check if connection requests exists by user and peer or peer and user (ID)
    boolean existsBySender_IdAndRecipient_IdOrSender_IdAndRecipient_Id(Long registeredPlayerId, Long blockedPlayerId, Long blockedPlayerId1, Long registeredPlayerId1);

    // Check if connection requests exists by user and peer or peer and user (player entities)
    boolean existsBySenderAndRecipientOrSenderAndRecipient(RegisteredPlayer registeredPlayer, RegisteredPlayer peer, RegisteredPlayer peer1, RegisteredPlayer registeredPlayer1);

}
