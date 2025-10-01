import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {DatePipe, NgOptimizedImage} from "@angular/common";
import {GamePlaySessionDTO} from '../../../dtos/game-play-sessions-dto';
import {GamePlaySessionResponseDTO} from '../../../dtos/game-play-session-response-dto';
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {FormsModule, NgForm} from '@angular/forms';
import {AdvancedAnalyticsResponseDTO} from '../../../dtos/analytic-dtos/advanced-analytics-response-dto';
import {GamePlaySessionSearchParamsDTO} from '../../../dtos/game-play-session-search-params-dto';

@Component({
  selector: 'app-game-play-sessions-list-component',
  imports: [
    NgOptimizedImage,
    DatePipe,
    FormsModule
  ],
  templateUrl: './game-play-sessions-list-component.html',
  styleUrl: './game-play-sessions-list-component.css'
})
export class GamePlaySessionListComponent{

  expanded: boolean = false;
  sessionSearch: boolean = false;
  selectedGame: GameResponseDTO | null = null;

  @Input() playedGames: GameResponseDTO[]  = [];
  @Input() playSessions: GamePlaySessionResponseDTO[] = [];

  @Output() delete =  new EventEmitter<number>;
  @Output() sessionLookup = new EventEmitter<GamePlaySessionSearchParamsDTO>();

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


  //<editor-fold desc="Submit">

  triggerSessionLookup(form: NgForm) {

    this.sessionSearch = true;


    const gamePlaySessionSearchParams = {

    }


    this.sessionLookup.emit(gamePlaySessionSearchParams)
    form.resetForm();
  }
  //</editor-fold>


}
