import { Component } from '@angular/core';
import {AddGamePlaySessionComponent} from './add-game-play-session-component/add-game-play-session-component';
import {GamePlaySessionsListComponent} from './game-play-sessions-list-component/game-play-sessions-list-component';
import {GamePlaySessionDTO} from '../../dtos/game-play-sessions-dto';
import {GamePlaySessionService} from '../../services/game-play-session-service';

@Component({
  selector: 'app-sessions-component',
  imports: [
    AddGamePlaySessionComponent,
    GamePlaySessionsListComponent
  ],
  templateUrl: './sessions-component.html',
  styleUrl: './sessions-component.css'
})
export class SessionsComponent {

  constructor(private gamePlaySessionService: GamePlaySessionService) {

  }


  handleSubmit(gamePlaySessionDTO: GamePlaySessionDTO) {
      this.gamePlaySessionService.createGamePlaySession(gamePlaySessionDTO).subscribe({
        next: (response: GamePlaySessionDTO) => {
          console.log(response)
        },
        error: (error: any) => console.error("fail", error)
      })
  }


}
