import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {ScoringModelEnum} from '../../../enums/scoring-model-enum';
import {SessionParticipantDTO} from '../../../dtos/session-participant-dto';
import {GamePlaySessionDTO} from '../../../dtos/game-play-sessions-dto';
import {NgOptimizedImage} from '@angular/common';
import {PlayerResponseDTO} from '../../../dtos/PlayerResponseDTO';
import {GameResponseDTO} from '../../../dtos/game-response-dto';


@Component({
  selector: 'app-add-game-play-session-component',
  imports: [
    FormsModule,
    NgOptimizedImage
  ],
  templateUrl: './add-game-play-session-component.html',
  styleUrl: './add-game-play-session-component.css'
})
export class AddGamePlaySessionComponent implements OnInit{


  expanded: boolean = false
  @Input()gameLookUpErrorMessage?: string;
  @Input()gameSelectErrorMessage?: string;
  @Input()participantErrorMessage?: string;
  @Input()resultsErrorMessage?: string;

  protected readonly ScoringModelEnum = ScoringModelEnum;


  @Input() network: PlayerResponseDTO[] = [];

  @Input() games: GameResponseDTO[] = [];




  //<editor-fold desc="Variables">


  numberPlayers: number = 1;
  cooperativeResult: number = 1;
  selectedGameName?: string;
  now = new Date();
  local = new Date(this.now.getTime() - this.now.getTimezoneOffset() * 60000);
  formattedDateTime = this.local.toISOString().slice(0, 16);

  //</editor-fold>

  //<editor-fold desc="GamePlaySessionDTO Constructor">
  sessionDateTime: string = this.formattedDateTime;
  scoringModel: ScoringModelEnum = ScoringModelEnum.RANKING;
  gameId: number = 0;
  sessionParticipants: SessionParticipantDTO[] = [];


  //</editor-fold>

  //<editor-fold desc="On Change Methods">


  onScoringModelChange(): void {
    if(this.scoringModel === 'RANKING') {
      this.numberPlayers = 1;
    }
    if(this.scoringModel === 'TEAM') {
      this.numberPlayers = 3;
    }
    if(this.scoringModel === 'COOPERATIVE') {
      this.numberPlayers = 2;
    }
  }


  onNumPlayerChange(): void {
      this.sessionParticipants = Array.from({length: this.numberPlayers}, (_, i) =>
        this.sessionParticipants[i] || {result: i + 1, playerId: null}
      );
  }



  onExpandChange() {
    this.expanded = !this.expanded;
  }



  //</editor-fold>

 ngOnInit() {
    this.onNumPlayerChange();
 }

  @Output() lookup = new EventEmitter<string>;

  triggerGameLookup(searchValue: string) {
    this.lookup.emit(searchValue);
  }

  triggerGameFilterClear() {
    this.games = [];
    this.gameId = 0;
  }

  triggerAddGame(id: number, gameTitle: string) {
    this.games = [];
    this.gameId = id;
    this.selectedGameName = gameTitle;
  }

  triggerGameRemove() {
    this.gameId = -1;
  }


  //<editor-fold desc="Submit">
  @Output() sessionSubmit = new EventEmitter<GamePlaySessionDTO>();
  triggerSubmit(form: NgForm) {

    const iso = new Date(this.sessionDateTime).toISOString();

    if(this.scoringModel === 'COOPERATIVE') {
      for(const sessionParticipant of this.sessionParticipants) {
        sessionParticipant.result = this.cooperativeResult;
      }
    }


    const gamePlayerSessionDTO = {
      sessionDateTime: iso,
      scoringModel: this.scoringModel,
      gameId: this.gameId,
      sessionParticipantDTOSet: this.sessionParticipants
    }


    this.sessionSubmit.emit(gamePlayerSessionDTO)
    this.sessionDateTime = this.formattedDateTime;
    this.scoringModel = ScoringModelEnum.RANKING;
    this.sessionParticipants = [];
    this.gameId = 0;
    this.numberPlayers = 1;
    this.onNumPlayerChange();

  }
  //</editor-fold>




}

