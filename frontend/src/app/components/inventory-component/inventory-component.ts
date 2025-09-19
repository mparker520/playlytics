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

  gameTitle: string = '';

  ownedGames: OwnedGameResponseDTO[] = [];


  constructor(private inventoryService: InventoryService) {

  }

  ngOnInit() : void {

    this.inventoryService.getInventory(this.gameTitle).subscribe({
      next: (response: OwnedGameResponseDTO[]) => {
        this.ownedGames = response;
        console.log(response);
      },
      error: (error) => console.error("fail", error)
    })

  }

}
