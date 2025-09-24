import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {OwnedGamesListComponent} from './owned-games-list-component/owned-games-list-component';
import {AddOwnedGameComponent} from './add-owned-game-component/add-owned-game-component';
import {InventoryService} from '../../services/inventory-service';
import {OwnedGameResponseDTO} from '../../dtos/owned-game-response-dto';
import {Component, OnInit} from '@angular/core';
import {GameResponseDTO} from '../../dtos/game-response-dto';
import {GameService} from '../../services/game-service';
import {
    AddGamePlaySessionComponent
} from "../sessions-component/add-game-play-session-component/add-game-play-session-component";
import {
    GamePlaySessionsListComponent
} from "../sessions-component/game-play-sessions-list-component/game-play-sessions-list-component";

@Component({
  selector: 'app-inventory-component',
    imports: [
        FormsModule,
        ReactiveFormsModule,
        OwnedGamesListComponent,
        AddOwnedGameComponent,
        AddGamePlaySessionComponent,
        GamePlaySessionsListComponent
    ],
  templateUrl: './inventory-component.html',
  styleUrl: './inventory-component.css'
})
export class InventoryComponent implements OnInit{

  //<editor-fold desc = "Constructor and Fields">

  inventoryFilter: string = '';
  ownedGames: OwnedGameResponseDTO[] = [];

  games: GameResponseDTO[] = [];


  constructor(private inventoryService: InventoryService, private gameService: GameService) {

  }

  //</editor-fold>

  //<editor-fold desc = "On Initiate">
// TODO: Fix filtering of Games
  ngOnInit() : void {

    this.inventoryService.getInventory().subscribe({
      next: (response: OwnedGameResponseDTO[]) => {
        this.ownedGames = response;
      },
      error: (error: any) => console.error("fail", error)
    })

  }

  //</editor-fold>

  //<editor-fold desc = "Handle Delete">

  handleDelete(id: number) {
      this.inventoryService.deleteOwnedGame(id).subscribe({
        next: (deleteResponse: void) => {
          this.inventoryService.getInventory().subscribe({
            next: (updateResponse: OwnedGameResponseDTO[]) => {
              this.ownedGames = updateResponse;
            },
            error: (error: any) => console.error("fail", error)
          })
        }
      })
  }

  //</editor-fold>

  //<editor-fold desc = "Get Games from Database">

  handleLookup(databaseFilter: string) {
    console.log(databaseFilter);
    this.gameService.getBoardGames(databaseFilter).subscribe({
          next:(response: GameResponseDTO[]) => {
            this.games = response;
            console.log(this.games);
          },

          error: (error: any) => console.error("fail", error)
    })
  }

  //</editor-fold>

  //<editor-fold desc="Add Game to Inventory">
  handleAdd(gameId: number) {
      this.inventoryService.addOwnedGame(gameId).subscribe({
        next: (addResponse: OwnedGameResponseDTO) => {
          this.inventoryService.getInventory().subscribe({
            next: (updateResponse: OwnedGameResponseDTO[]) => {
              this.ownedGames = updateResponse;
            },
            error: (error: any) => console.error("fail", error)
          })
        }
      })
  }
  //</editor-fold>

}
