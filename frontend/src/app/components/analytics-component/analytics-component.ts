import {Component, OnInit} from '@angular/core';
import {WinLossComponent} from './win-loss-component/win-loss-component';
import {GamePlaySessionService} from '../../services/game-play-session-service';
import {GameResponseDTO} from '../../dtos/game-response-dto';
import {OwnedGameFrequencyComponent} from './owned-game-frequency-component/owned-game-frequency-component';
import {InventoryService} from '../../services/inventory-service';
import {OwnedGameResponseDTO} from '../../dtos/owned-game-response-dto'
import {PlayTrendsComponent} from './play-trends-component/play-trends-component';

@Component({
  selector: 'app-analytics-component',
  standalone: true,
  imports: [
    WinLossComponent,
    OwnedGameFrequencyComponent,
    PlayTrendsComponent
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
      next: (playedGameResponse: GameResponseDTO[]) => {
        this.playedGames = playedGameResponse;

      },
      error: (error: any) => console.error("fail", error)
    })


    this.inventoryService.getInventory().subscribe({
      next: (inventoryResponse: OwnedGameResponseDTO[]) => {
        this.ownedGames = inventoryResponse;

      },
      error: (error: any) => console.error("fail", error)
    })


  }



}
