import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {OwnedGamesListComponent} from './owned-games-list-component/owned-games-list-component';
import {AddOwnedGameComponent} from './add-owned-game-component/add-owned-game-component';
import {InventoryService} from '../../services/inventory-service';
import {OwnedGameResponseDTO} from '../../dtos/owned-game-response-dto';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-inventory-component',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    OwnedGamesListComponent,
    AddOwnedGameComponent
  ],
  templateUrl: './inventory-component.html',
  styleUrl: './inventory-component.css'
})
export class InventoryComponent implements OnInit{

  //<editor-fold desc = "Constructor and Fields">

  gameTitle: string = '';
  ownedGames: OwnedGameResponseDTO[] = [];


  constructor(private inventoryService: InventoryService) {

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
        next: (response: OwnedGameResponseDTO[]) => {
          this.ownedGames = response;
        },
        error: (error: any) => console.error("fail", error)

      })
  }

  //</editor-fold>


}
