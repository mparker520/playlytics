import {Component, OnInit} from '@angular/core';
import {WinLossComponent} from './win-loss-component/win-loss-component';
import {GamePlaySessionService} from '../../services/game-play-session-service';
import {GameResponseDTO} from '../../dtos/game-response-dto';
import {OwnedGameFrequencyComponent} from './owned-game-frequency-component/owned-game-frequency-component';
import {InventoryService} from '../../services/inventory-service';
import {OwnedGameResponseDTO} from '../../dtos/owned-game-response-dto'

@Component({
  selector: 'app-analytics-component',
  standalone: true,
  imports: [
    WinLossComponent,
    OwnedGameFrequencyComponent
  ],
  templateUrl: './analytics-component.html',
  styleUrl: './analytics-component.css'
})
export class AnalyticsComponent implements OnInit {

  constructor(private gamePlaySessionService: GamePlaySessionService, private inventoryService: InventoryService) {
  }

  playedGames: GameResponseDTO[] = [];
  ownedGames: OwnedGameResponseDTO[] = [];

  ngOnInit() {

    this.gamePlaySessionService.getAllPlayedGames().subscribe({
      next: (response: GameResponseDTO[]) => {
        this.playedGames = response;

      },
      error: (error: any) => console.error("fail", error)
    })

    this.inventoryService.getInventory().subscribe({
      next: (response: OwnedGameResponseDTO[]) => {
        this.ownedGames = response;

      },
      error: (error: any) => console.error("fail", error)
    })


  }



}
