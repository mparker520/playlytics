import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {ScoringModelEnum} from '../../../enums/scoring-model-enum';
import {SessionParticipantDTO} from '../../../dtos/session-participant-dto';
import {SessionTeamDTO} from '../../../dtos/session-team-dto';
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
export class AddGamePlaySessionComponent {

expanded: boolean = false


  protected readonly ScoringModelEnum = ScoringModelEnum;


  @Input() network: PlayerResponseDTO[] = [];

  @Input() games: GameResponseDTO[] = [];


  //</editor-fold>

  //<editor-fold desc="Variables">
  numberPlayers: number = 0;
  numberTeams: number = 0;
  cooperativeResult: number = 1;
  selectedGameName?: string;


  //</editor-fold>

  //<editor-fold desc="GamePlaySessionDTO Constructor">
  sessionDateTime!: string;
  scoringModel: ScoringModelEnum = ScoringModelEnum.RANKING;
  gameId!: number;
  sessionParticipants: SessionParticipantDTO[] = [];
  sessionTeams: SessionTeamDTO[] = [];



  //</editor-fold>

  //<editor-fold desc="On Change Methods">




  onNumPlayerChange(): void {
      this.sessionParticipants = Array.from({length: this.numberPlayers}, (_, i) =>
        this.sessionParticipants[i] || {result: 0, playerId: 0}
      );
  }

  onNumTeamChange(): void {
    this.sessionTeams = Array.from({length: this.numberTeams}, (_, i) =>
      this.sessionTeams[i] || {result: 0, playerIds: [], teamName: ''}
    );
  }



  /* onTeamRankChange(teamNumber: number): void {

   for(const sessionParticipant of this.sessionParticipants) {

     if(sessionParticipant.teamNumber === teamNumber) {

        sessionParticipant.result = this.sessionTeams[teamNumber].result;
      }
(ngModelChange)="onTeamRankChange(i)"
   }
  } */

 /* onTeamAssociationChange(sessionParticipantIndex: number, sessionParticipantId: number): void {

        let teamNumber = (this.sessionParticipants)[sessionParticipantIndex].teamNumber;

        this.sessionTeams[teamNumber].playerIds.push(sessionParticipantId);
 (change)="onTeamAssociationChange(i, sessionParticipant.playerId)
      } */


  onExpandChange() {
    this.expanded = !this.expanded;
  }

  //</editor-fold>



  @Output() lookup = new EventEmitter<string>;
  triggerGameLookup(searchValue: string) {
    this.lookup.emit(searchValue);
  }

  triggerGameFilterClear() {
    this.games = [];
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
      sessionParticipantDTOSet: this.sessionParticipants,
      sessionTeamDTOSet: this.sessionTeams
    }


    this.sessionSubmit.emit(gamePlayerSessionDTO)
    form.resetForm();
  }
  //</editor-fold>




}

