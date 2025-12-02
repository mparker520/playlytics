import {Component, EventEmitter, Output} from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {
  AddGamePlaySessionComponent
} from '../sessions-component/add-game-play-session-component/add-game-play-session-component';
import {
  GamePlaySessionListComponent
} from '../sessions-component/game-play-sessions-list-component/game-play-sessions-list-component';
import {
  PendingGamePlaySessionsComponent
} from '../sessions-component/pending-game-play-sessions-component/pending-game-play-sessions-component';

import {GamePlaySessionResponseDTO} from '../../dtos/game-play-session-response-dto';
import {GameService} from '../../services/game-service';

@Component({
  selector: 'app-board-games-component',
  imports: [
    NgOptimizedImage,
    AddGamePlaySessionComponent,
    GamePlaySessionListComponent,
    PendingGamePlaySessionsComponent
  ],
  templateUrl: './games-component.html',
  styleUrl: './games-component.css'
})
export class GamesComponent {

  gameAddStatusMessage?: string;

  constructor(private gameService: GameService) {
  }

  //<editor-fold desc="Submit Game Function">

    submitGame(boardGame: string) {

        this.gameService.addBoardGame(boardGame).subscribe({
          next: (response: string) => {
            this.gameAddStatusMessage = "You have successfully added your game to the database!"

            setTimeout(() => {
              this.gameAddStatusMessage = undefined;
            }, 3000);
          },

          error: (error: any) => {
            this.gameAddStatusMessage = error.error.message

            setTimeout(() => {
              this.gameAddStatusMessage = undefined;
            }, 3000);
          }

      })
    }


  //</editor-fold>


}
