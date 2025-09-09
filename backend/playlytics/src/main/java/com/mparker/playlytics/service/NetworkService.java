package com.mparker.playlytics.service;

// Imports


import com.mparker.playlytics.entity.GhostPlayer;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.repository.GhostPlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NetworkService {


    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GhostPlayerRepository ghostPlayerRepository;


    public NetworkService(RegisteredPlayerRepository registeredPlayerRepository, GhostPlayerRepository ghostPlayerRepository) {
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.ghostPlayerRepository = ghostPlayerRepository;
    }

    //</editor-fold>


    //<editor-fold desc = "Remove Associations">

    @Transactional
    public void removeAssociation(Long registeredPlayerId, Long ghostPlayerId) {

       RegisteredPlayer registeredPlayer = registeredPlayerRepository.getReferenceById(registeredPlayerId);
       GhostPlayer ghostPlayer = ghostPlayerRepository.getReferenceById(ghostPlayerId);

        registeredPlayer.getAssociations().remove(ghostPlayer);

    }

    //</editor-fold>

}
