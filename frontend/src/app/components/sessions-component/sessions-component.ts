import {Component, OnInit} from '@angular/core';
import {AddGamePlaySessionComponent} from './add-game-play-session-component/add-game-play-session-component';

import {GamePlaySessionDTO} from '../../dtos/game-play-sessions-dto';
import {GamePlaySessionService} from '../../services/game-play-session-service';
import {GamePlaySessionListComponent} from './game-play-sessions-list-component/game-play-sessions-list-component';
import {OwnedGameResponseDTO} from '../../dtos/owned-game-response-dto';
import {GamePlaySessionResponseDTO} from '../../dtos/game-play-session-response-dto';
import {AddOwnedGameComponent} from '../inventory-component/add-owned-game-component/add-owned-game-component';
import {GameResponseDTO} from '../../dtos/game-response-dto';
import {GameService} from '../../services/game-service';

@Component({
  selector: 'app-sessions-component',
  imports: [
    AddGamePlaySessionComponent,
    GamePlaySessionListComponent,
    AddOwnedGameComponent
  ],
  templateUrl: './sessions-component.html',
  styleUrl: './sessions-component.css'
})
export class SessionsComponent implements OnInit{

  //<editor-fold desc="Constructor">

  playSessions: GamePlaySessionResponseDTO[] = [];
  games: GameResponseDTO[] = [];

  constructor(private gamePlaySessionService: GamePlaySessionService, private gameService: GameService) {

  }
  //</editor-fold>


  //<editor-fold desc = "On Initiate">

  ngOnInit() : void {

  this.gamePlaySessionService.getGamePlaySessions().subscribe({
      next: (response: GamePlaySessionResponseDTO[]) => {
        this.playSessions = response;
        console.log(this.playSessions);
      },
      error: (error: any) => console.error("fail", error)
    })


  }

  //</editor-fold>

  //<editor-fold desc="Handle Game Lookup">
  handleGameLookup(databaseFilter: string) {
    const now = new Date()
    console.log('game lookup' + now)
    this.gameService.getBoardGames(databaseFilter).subscribe({
      next:(response: GameResponseDTO[]) => {
        this.games = response;
      },

      error: (error: any) => console.error("fail", error)
    })
  }
  //</editor-fold>

  //<editor-fold desc="Handle Game Play Session Submission">
  handleSubmit(gamePlaySessionDTO: GamePlaySessionDTO) {
    const now = new Date()
    console.log('game submti triggered' + now);
      this.gamePlaySessionService.createGamePlaySession(gamePlaySessionDTO).subscribe({
        next: (response: GamePlaySessionDTO) => {
          console.log(response)
        },
        error: (error: any) => console.error("fail", error)
      })
  }
  //</editor-fold>

  handleSessionDelete(id: number) {
    this.gamePlaySessionService.deleteByIdAndPlayerId(id).subscribe({
      next:(deleteResponse: void) => {
        this.gamePlaySessionService.getGamePlaySessions().subscribe({
          next: (gamePlaySessionResponse: GamePlaySessionResponseDTO[]) => {
            this.playSessions = gamePlaySessionResponse;
          },
          error: (gamePlaySessionResponseError: any) => console.error("fail", gamePlaySessionResponseError)
        })
      },
      error: (deleteError: any) => console.error("fail", deleteError)
    })
  }


}
