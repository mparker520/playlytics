package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.repository.GhostPlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class GhostPlayerService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final GhostPlayerRepository ghostPlayerRepository;

    public GhostPlayerService(GhostPlayerRepository ghostPlayerRepository) {
        this.ghostPlayerRepository = ghostPlayerRepository;
    }

    //</editor-fold>

    // <editor-fold desc = "Create GhostPlayer">

    @Transactional
    public GhostPlayer saveGhostPlayer(GhostPlayer ghostPlayer) {

        return ghostPlayerRepository.save(ghostPlayer);

    }

    // </editor-fold>

    // <editor-fold desc = "Update GhostPlayer">



    // </editor-fold>

    // <editor-fold desc = "View GhostPlayers">


    // </editor-fold>

    // Currently does not support Delete Function for GhostPlayers

}
