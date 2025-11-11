package com.mparker.playlytics;

// Imports
import com.mparker.playlytics.dto.RegisteredPlayerResponseDTO;
import com.mparker.playlytics.entity.BlockedRelationship;
import com.mparker.playlytics.entity.BlockedRelationshipId;
import com.mparker.playlytics.entity.Player;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.repository.BlockedRelationshipRepository;
import com.mparker.playlytics.repository.PlayerRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import com.mparker.playlytics.service.NetworkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class NetworkServiceTest {

    @Autowired
    private BlockedRelationshipRepository blockedRelationshipRepository;


    @Autowired
    private RegisteredPlayerRepository registeredPlayerRepository;

    @Autowired
    private NetworkService networkService;


    @Test
    void testSearchOnBlockedPlayer() {

        // Arrange: Create Players and Block


        RegisteredPlayer jane = new RegisteredPlayer("Jane", "Doe", null, "janeDoeDoe", "jane@doe.com", "janedoe123");
        RegisteredPlayer john = new RegisteredPlayer("John", "Smith", null, "johnSmith", "john@smith.com", "johnsmith123");

        registeredPlayerRepository.save(jane);
        registeredPlayerRepository.save(john);

        assertNotNull(jane.getId());
        assertNotNull(john.getId());

        BlockedRelationshipId blockId = new BlockedRelationshipId(jane.getId(), john.getId());
        BlockedRelationship blockedRelationship = new BlockedRelationship(blockId, jane, john);

        blockedRelationshipRepository.save(blockedRelationship);

        assertTrue(blockedRelationshipRepository.existsByBlockerAndBlockedOrBlockerAndBlocked(jane, john, john, jane));



        // Act + Assert - John Searches for Jane by Email, Result should throw NotFoundException Error

        assertThrows(NotFoundException.class, () ->
                        networkService.getAvailablePeersByFilter("jane@doe.com", john.getId()),
                "Blocked Player should not be able to find Blocker"
        );


    }



}