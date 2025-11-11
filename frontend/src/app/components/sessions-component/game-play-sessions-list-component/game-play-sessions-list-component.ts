import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {DatePipe, NgOptimizedImage} from "@angular/common";
import {GamePlaySessionDTO} from '../../../dtos/game-play-sessions-dto';
import {GamePlaySessionResponseDTO} from '../../../dtos/game-play-session-response-dto';
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {FormsModule, NgForm} from '@angular/forms';
import {AdvancedAnalyticsResponseDTO} from '../../../dtos/analytic-dtos/advanced-analytics-response-dto';
import {GamePlaySessionSearchParamsDTO} from '../../../dtos/game-play-session-search-params-dto';
import {MatDateRangeInput} from '@angular/material/datepicker';

@Component({
  selector: 'app-game-play-sessions-list-component',
  imports: [
    NgOptimizedImage,
    DatePipe,
    FormsModule,

  ],
  templateUrl: './game-play-sessions-list-component.html',
  styleUrl: './game-play-sessions-list-component.css'
})
export class GamePlaySessionListComponent{

  expanded: boolean = false;
  sessionSearch: boolean = false;
  selectedGame: GameResponseDTO | null = null;
  startDate: string = new Date(new Date().setUTCDate(new Date().getUTCDate() - 1)).toISOString().split("T")[0];
  endDate: string = new Date().toISOString().split("T")[0];

  @Input() reportGenerationTime?: Date;
  @Input() playedGames: GameResponseDTO[]  = [];
  @Input() filteredPlayedGames: GameResponseDTO[] = [];
  @Input() playSessions: GamePlaySessionResponseDTO[] = [];

  @Output() delete =  new EventEmitter<number>;
  @Output() sessionLookup = new EventEmitter<GamePlaySessionSearchParamsDTO>();


  ngOnChanges(changes: SimpleChanges) {
    if(changes['playedGames'] && this.playedGames) {
      this.filteredPlayedGames = this.playedGames;
    }
  }


  //<editor-fold desc="On Expand Change">
  onExpandChange() {
    this.expanded = !this.expanded;
  }
  //</editor-fold>

  //<editor-fold desc="Session Delete">
  triggerDelete(id: number) {
    this.delete.emit(id)

  }
  //</editor-fold>


  //<editor-fold desc="Look Up Sessions">

  triggerSessionLookup(form: NgForm) {


    this.sessionSearch = true;


    const gamePlaySessionSearchParams: any = {
          startDate: this.startDate,
          endDate: this.endDate
    }

    if (this.selectedGame?.gameId != null) {
      gamePlaySessionSearchParams.selectedGame = this.selectedGame.gameId;
    }

    this.sessionLookup.emit(gamePlaySessionSearchParams)


  }
  //</editor-fold>

  //<editor-fold desc="Clear Report">
  clearReport() {
    this.sessionSearch = false;
  }
  //</editor-fold>

  //<editor-fold desc="Filter Sessions by Game Name">
  filterSessionsByGame(searchGameBox: string) {

    this.selectedGame = this.filteredPlayedGames[0];

    this.filteredPlayedGames = this.playedGames.filter(playedGames => {
        return playedGames.title.toLowerCase().includes(searchGameBox.toLowerCase());
      }

    );
  }
  //</editor-fold>


}
