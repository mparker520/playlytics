import {Component, OnInit} from '@angular/core';
import {AddGamePlaySessionComponent} from './add-game-play-session-component/add-game-play-session-component';

import {GamePlaySessionDTO} from '../../dtos/game-play-sessions-dto';
import {GamePlaySessionService} from '../../services/game-play-session-service';
import {GamePlaySessionListComponent} from './game-play-sessions-list-component/game-play-sessions-list-component';
import {OwnedGameResponseDTO} from '../../dtos/owned-game-response-dto';
import {GamePlaySessionResponseDTO} from '../../dtos/game-play-session-response-dto';

@Component({
  selector: 'app-sessions-component',
  imports: [
    AddGamePlaySessionComponent,
    GamePlaySessionListComponent
  ],
  templateUrl: './sessions-component.html',
  styleUrl: './sessions-component.css'
})
export class SessionsComponent implements OnInit{

  //<editor-fold desc="Constructor">

  playSessions: GamePlaySessionResponseDTO[] = [];

  constructor(private gamePlaySessionService: GamePlaySessionService) {

  }
  //</editor-fold>




  //<editor-fold desc = "On Initiate">

  ngOnInit() : void {

    this.gamePlaySessionService.getGamePlaySessions().subscribe({
      next: (response: GamePlaySessionResponseDTO[]) => {
        this.playSessions = response;
      },
      error: (error: any) => console.error("fail", error)
    })

  }

  //</editor-fold>



  //<editor-fold desc="Handle Game Play Session Submission">
  handleSubmit(gamePlaySessionDTO: GamePlaySessionDTO) {
      this.gamePlaySessionService.createGamePlaySession(gamePlaySessionDTO).subscribe({
        next: (response: GamePlaySessionDTO) => {
          console.log(response)
        },
        error: (error: any) => console.error("fail", error)
      })
  }
  //</editor-fold>


}
