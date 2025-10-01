import {Component, OnInit} from '@angular/core';
import {AddGamePlaySessionComponent} from './add-game-play-session-component/add-game-play-session-component';
import {GamePlaySessionDTO} from '../../dtos/game-play-sessions-dto';
import {GamePlaySessionService} from '../../services/game-play-session-service';
import {GamePlaySessionListComponent} from './game-play-sessions-list-component/game-play-sessions-list-component';
import {GamePlaySessionResponseDTO} from '../../dtos/game-play-session-response-dto';
import {GameResponseDTO} from '../../dtos/game-response-dto';
import {GameService} from '../../services/game-service';
import {PlayerResponseDTO} from '../../dtos/PlayerResponseDTO';
import {NetworkService} from '../../services/network-service';
import {RegisteredPlayerService} from '../../services/registered-player-service';
import {PendingGamePlaySessionsComponent} from './pending-game-play-sessions-component/pending-game-play-sessions-component';
import {GamePlaySessionSearchParamsDTO} from '../../dtos/game-play-session-search-params-dto';

@Component({
  selector: 'app-sessions-component',
  imports: [
    AddGamePlaySessionComponent,
    GamePlaySessionListComponent,
    PendingGamePlaySessionsComponent

  ],
  templateUrl: './sessions-component.html',
  styleUrl: './sessions-component.css'
})
export class SessionsComponent implements OnInit{

  //<editor-fold desc="Constructor">

  playedGames: GameResponseDTO[] = [];
  playSessions: GamePlaySessionResponseDTO[] = [];
  pendingPlaySessions: GamePlaySessionResponseDTO[] = [];
  games: GameResponseDTO[] = [];
  network: PlayerResponseDTO[] = [];

  constructor(private gamePlaySessionService: GamePlaySessionService, private gameService: GameService, private networkService: NetworkService, private registeredPlayerService: RegisteredPlayerService) {

  }
  //</editor-fold>


  //<editor-fold desc = "On Initiate">

  ngOnInit() : void {


    this.gamePlaySessionService.getAllPlayedGames().subscribe({
      next: (playedGameResponse: GameResponseDTO[]) => {
        this.playedGames = playedGameResponse;

      },
      error: (error: any) => console.error("fail", error)
    })

    this.gamePlaySessionService.getPendingGamePlaySessions().subscribe({
      next: (response: GamePlaySessionResponseDTO[]) => {
        this.pendingPlaySessions = response;
      },
      error: (error: any) => console.error("fail", error)
    })

    this.networkService.getNetwork().subscribe({
      next: (response: PlayerResponseDTO[]) => {
        this.network = response;
      },
      error: (error: any) => console.error("fail", error)
    })

  }

  //</editor-fold>

  //<editor-fold desc="Handle Game Lookup">
  handleGameLookup(databaseFilter: string) {
    const now = new Date()
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

      this.gamePlaySessionService.createGamePlaySession(gamePlaySessionDTO).subscribe({
        next: (response: GamePlaySessionDTO) => {

        },
        error: (error: any) => console.error("fail", error)
      })
  }
  //</editor-fold>


  //<editor-fold desc="Handle Session Delete">
  handleSessionDelete(id: number) {
    this.gamePlaySessionService.deleteByIdAndPlayerId(id).subscribe({
      next:(deleteResponse: void) => {

      },
      error: (deleteError: any) => console.error("fail", deleteError)
    })
  }
  //</editor-fold>


  //<editor-fold desc="Handle Session Accept">
  handleSessionAccept(id: number) {
    this.gamePlaySessionService.acceptGamePlaySession(id).subscribe({
      next: (response: void) => {

        this.gamePlaySessionService.getPendingGamePlaySessions().subscribe({
          next: (response: GamePlaySessionResponseDTO[]) => {
            this.pendingPlaySessions = response;
          },
          error: (error: any) => console.error("fail", error)
        })
      },
      error:(error: any) => console.error('fail', error)
    })
  }
  //</editor-fold>


  handleSessionLookup(params: GamePlaySessionSearchParamsDTO) {


    this.gamePlaySessionService.getGamePlaySessions(params).subscribe({
      next: (response: GamePlaySessionResponseDTO[]) => {
        this.playSessions = response;
      },
      error: (error: any) => console.error("fail", error)
    })


  }

}
