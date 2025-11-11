package com.mparker.playlytics;

// Imports
import com.mparker.playlytics.dto.RegisteredPlayerUpdateDTO;
import com.mparker.playlytics.entity.BlockedRelationship;
import com.mparker.playlytics.entity.BlockedRelationshipId;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.exception.CustomAccessDeniedException;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.repository.BlockedRelationshipRepository;
import com.mparker.playlytics.repository.PlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import com.mparker.playlytics.service.NetworkService;
import com.mparker.playlytics.service.RegisteredPlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RegisteredPlayerServiceTest {


    @Autowired
    private RegisteredPlayerService registeredPlayerService;

    @Autowired
    private RegisteredPlayerRepository registeredPlayerRepository;

    @Test
    void testUpdateOnProfile() {

        // Arrange: Create Players and Block


        RegisteredPlayer jane = new RegisteredPlayer("Jane", "Doe", null, "janeDoeDoe", "jane@doe.com", "janedoe123");
        RegisteredPlayer john = new RegisteredPlayer("John", "Smith", null, "johnSmith", "john@smith.com", "johnsmith123");

        registeredPlayerRepository.save(jane);
        registeredPlayerRepository.save(john);

        assertNotNull(jane.getId());
        assertNotNull(john.getId());

        // Act + Assert - Jane cannot update her email address to an exist email address

        RegisteredPlayerUpdateDTO registeredPlayerUpdateDTO = new RegisteredPlayerUpdateDTO("Jane", "Doe",  "janeDoeDoeForever", "john@smith.com");

        assertThrows(CustomAccessDeniedException.class, () ->
                       registeredPlayerService.updateRegisteredPlayer(jane.getId(), registeredPlayerUpdateDTO),
                "A player cannot update their email address if another player is already registered under the same email address."
        );


    }



}