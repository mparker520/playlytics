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
import {BoardGamesService} from '../../services/board-games-service';
import {GamePlaySessionResponseDTO} from '../../dtos/game-play-session-response-dto';

@Component({
  selector: 'app-board-games-component',
  imports: [
    NgOptimizedImage,
    AddGamePlaySessionComponent,
    GamePlaySessionListComponent,
    PendingGamePlaySessionsComponent
  ],
  templateUrl: './board-games-component.html',
  styleUrl: './board-games-component.css'
})
export class BoardGamesComponent {


constructor(private boardGameService: BoardGamesService) {
}

  //<editor-fold desc="Submit Game Function">

    submitGame(boardGame: string) {
        this.boardGameService.addBoardGame(boardGame).subscribe({
          next: (response: string) => {
            console.log(response);
          },
          error: (error: any) => console.error("fail", error)
        });
    }


  //</editor-fold>


}
