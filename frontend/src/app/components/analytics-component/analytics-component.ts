import {Component, OnInit} from '@angular/core';
import {WinLossComponent} from './win-loss-component/win-loss-component';
import {GamePlaySessionService} from '../../services/game-play-session-service';
import {GameResponseDTO} from '../../dtos/game-response-dto';



@Component({
  selector: 'app-analytics-component',
  standalone: true,
  imports: [
    WinLossComponent
  ],
  templateUrl: './analytics-component.html',
  styleUrl: './analytics-component.css'
})
export class AnalyticsComponent implements OnInit {

  constructor(private gamePlaySessionService: GamePlaySessionService) {
  }

  playedGames: GameResponseDTO[] = [

  ];

  ngOnInit() {
    this.gamePlaySessionService.getAllPlayedGames().subscribe({
      next: (response: GameResponseDTO[]) => {
        this.playedGames = response;
        console.log(response)
      },
      error: (error: any) => console.error("fail", error)
    })

  }



}
