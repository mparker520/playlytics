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


  submitGame(boardGame: string) {

  }


}
