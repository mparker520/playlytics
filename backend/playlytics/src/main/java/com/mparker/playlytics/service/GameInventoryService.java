package com.mparker.playlytics.service;

// Imports
import com.mparker.playlytics.dto.OwnedGameResponseDTO;
import com.mparker.playlytics.entity.Game;
import com.mparker.playlytics.entity.OwnedGame;
import com.mparker.playlytics.entity.RegisteredPlayer;
import com.mparker.playlytics.exception.NotFoundException;
import com.mparker.playlytics.repository.GameRepository;
import com.mparker.playlytics.repository.OwnedGameRepository;
import com.mparker.playlytics.repository.RegisteredPlayerRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service

public class GameInventoryService {

    //<editor-fold desc = "Constructors and Dependencies">

    private final OwnedGameRepository ownedGameRepository;
    private final RegisteredPlayerRepository registeredPlayerRepository;
    private final GameRepository gameRepository;

    public GameInventoryService(OwnedGameRepository ownedGameRepository, RegisteredPlayerRepository registeredPlayerRepository, GameRepository gameRepository) {
        this.ownedGameRepository = ownedGameRepository;
        this.registeredPlayerRepository = registeredPlayerRepository;
        this.gameRepository = gameRepository;

    }

    //</editor-fold>

    // <editor-fold desc = "Add OwnedGame to RegisteredPlayer Inventory">

    @Transactional
    public OwnedGameResponseDTO saveOwnedGame(Long registeredPlayerId, Long gameId, Long authUserId) throws AccessDeniedException, NotFoundException {

        if(!registeredPlayerId.equals(authUserId)) {
            throw new AccessDeniedException("You Do Not Have Access to This Resource");
        }

        else {

            RegisteredPlayer player = registeredPlayerRepository.getReferenceById(registeredPlayerId);
            Optional<Game> game = gameRepository.findById(gameId);

            // Check that Game Exists by Id
            if(game.isPresent()) {

                Game gameObj = game.get();
                OwnedGame ownedGame = new OwnedGame(player, gameObj);

                // Save OwnedGame
                ownedGameRepository.save(ownedGame);


                // Create and Return GameResponseDTO
                String gameName = ownedGame.getGame().getGameTitle();

                return  new OwnedGameResponseDTO(gameId, gameName);

            }

            else {

                throw new NotFoundException("Game Not Found");

            }


        }


    }

    // </editor-fold>

    // <editor-fold desc = "View OwnedGames in RegisteredPlayer Inventory">


    // View OwnedGames Helper Method to Create List of OwnedGameResponseDTOs

    private List<OwnedGameResponseDTO> getOwnedGamesDTOList(List<OwnedGame> ownedGamesList) {

        List<OwnedGameResponseDTO> ownedGamesResponseDTOList = new ArrayList<>();

        for (OwnedGame ownedGame : ownedGamesList) {

            Long gameId = ownedGame.getGame().getId();
            String gameName = ownedGame.getGame().getGameTitle();

            OwnedGameResponseDTO ownedGamesResponseDTO = new OwnedGameResponseDTO(gameId, gameName);
            ownedGamesResponseDTOList.add(ownedGamesResponseDTO);

        }

        return ownedGamesResponseDTOList;

    }


    // Returns all RegisteredPlayer's OwnedGames
    @Transactional(readOnly = true)
    public List<OwnedGameResponseDTO> findAllByRegisteredPlayerId(Long registeredPlayerId, Long authUserId) throws AccessDeniedException {

        if(!registeredPlayerId.equals(authUserId)) {
            throw new AccessDeniedException("You Do Not Have Access to This Resource");
        }

        else {

            List<OwnedGame> ownedGamesList = ownedGameRepository.findAllByRegisteredPlayer_Id(registeredPlayerId);

            if(ownedGamesList.isEmpty()) {
                throw new NotFoundException("You Do Not Have Any Games in Your Inventory!");
            }

            else {
                return getOwnedGamesDTOList(ownedGamesList);
            }

        }

    }


    // Returns RegisteredPlayer's OwnedGames by Game Title
    @Transactional(readOnly = true)
    public List<OwnedGameResponseDTO> findByRegisteredPlayerIDAndTitle(Long registeredPlayerId, String gameName, Long authUserId) throws AccessDeniedException, NotFoundException {

        if(!registeredPlayerId.equals(authUserId)) {
            throw new AccessDeniedException("You Do Not Have Access to This Resource");
        }

        else {
            List<OwnedGame> ownedGamesByNameList = ownedGameRepository.findAllByRegisteredPlayer_IdAndGame_gameTitle(registeredPlayerId, gameName);

            if(ownedGamesByNameList.isEmpty()) {
                throw new NotFoundException("No Games By That Name Found in Inventory");
            }
            return getOwnedGamesDTOList(ownedGamesByNameList);
        }

    }


    // </editor-fold>

    // <editor-fold desc = "Remove OwnedGame from RegisteredPlayer Inventory">


    // Delete OwnedGame by OwnedGame_Id and current Player_Id
    @Transactional
    public void deleteByIdAndPlayerId(Long registeredPlayerId, Long ownedGameId, Long authUserId) throws AccessDeniedException, NotFoundException {

        if(!registeredPlayerId.equals(authUserId)) {
            throw new AccessDeniedException("You Do Not Have Access to This Resource");
        }

        else {

            if(!ownedGameRepository.existsById(ownedGameId)) {
                throw new NotFoundException("No Game with that Id Found in Inventory");
            }

            else {
                ownedGameRepository.deleteByIdAndRegisteredPlayer_Id(ownedGameId, registeredPlayerId);
            }
        }

    }


    // </editor-fold>

}
