package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.stereotype.Service;


@Service

public class RegisteredPlayerService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final RegisteredPlayerRepository registeredPlayerRepository;

    public RegisteredPlayerService(RegisteredPlayerRepository registeredPlayerRepository) {
        this.registeredPlayerRepository = registeredPlayerRepository;
    }

    //</editor-fold>

    //<editor-fold desc = "Update RegisteredPlayer">
    //</editor-fold>

    /*  TODO: Create RegisteredPlayer Services after further wiring of Controllers
    //<editor-fold desc = "Lookup RegisteredPlayer">

    // Lookup by login Email to Send Connection Request
    private Optional<RegisteredPlayerDTO> findRegisteredPlayerByLoginEmail(String loginEmail) {
        // Look up if existing connection request
        // If connection request status does not equal blocked
        // Look up user by loginEmail
        // Return User DTO

    }


    // Lookup by Display Name to Send Connection Request
    private Optional<RegisteredPlayerDTO> findRegisteredPlayerByLoginEmail(String loginEmail) {
        // Look up if existing connection request
        // If connection request status does not equal blocked
        // Look up user by Display Name
        // Return User DTO

    }


    // Lookup All Connections



    // Lookup All Associations


    //</editor-fold>

    //<editor-fold desc = "Delete RegisteredPlayer">

    // Convert to GhostPlayer
    // Disable Connections
    // Remove row from table

    //</editor-fold>

    //<editor-fold desc = "Connections">

    // Send Connection Request

    // Confirm Connection Request

    // Remove Connection

    // Decline Connection Request

    // Block Connection Request

    // Disable Connection Request through Player Deletion

    //</editor-fold>
*/

}
