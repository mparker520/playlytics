import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {ScoringModelEnum} from '../../../enums/scoring-model-enum';
import {SessionParticipantDTO} from '../../../dtos/session-participant-dto';
import {SessionTeamDTO} from '../../../dtos/session-team-dto';
import {ConnectionDTO} from '../../../dtos/connection-dto';
import {GamePlaySessionDTO} from '../../../dtos/game-play-sessions-dto';
import {NgOptimizedImage} from '@angular/common';
import {GamePlaySessionService} from '../../../services/game-play-session-service';


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


  //<editor-fold desc="HARDCODED PLAYERS">
  connections: ConnectionDTO[]=[
    {id: 1, firstName: 'Melissa', lastName: 'Parker', displayName: 'The Coding Wabs', email: 'melissaparker520@gmail.com'},
    {id: 2, firstName: 'Joe', lastName: 'Parker', displayName: 'The Dice Master', email: 'sample@gmail.com'}
  ];
  //</editor-fold>

  //<editor-fold desc="Variables">
  numberPlayers: number = 0;
  numberTeams: number = 0;
  cooperativeResult: number = -1;
  creatorId: number = 1;
  //</editor-fold>

  //<editor-fold desc="GamePlaySessionDTO Constructor">
  sessionDateTime!: string;
  scoringModel: ScoringModelEnum = ScoringModelEnum.RANKING;
  gameId!: number;
  sessionParticipants: SessionParticipantDTO[] = []
  sessionTeams: SessionTeamDTO[] = [];



  //</editor-fold>

  //<editor-fold desc="On Change Methods">



  onNumPlayerChange(): void {
      this.sessionParticipants = Array.from({length: this.numberPlayers}, (_, i) =>
        this.sessionParticipants[i] || {result: 0, playerId: 0, teamNumber: null}
      );
  }

  onNumTeamChange(): void {
    this.sessionTeams = Array.from({length: this.numberTeams}, (_, i) =>
      this.sessionTeams[i] || {result: 0, playerIds: [], teamName: ''}
    );
  }

  onCooperativeResultChange(): void {
        for(const sessionParticipant of this.sessionParticipants) {
          sessionParticipant.result = this.cooperativeResult;
        }
  }

  onTeamRankChange(teamNumber: number): void {

    for(const sessionParticipant of this.sessionParticipants) {
      if(sessionParticipant.teamNumber === teamNumber) {
        sessionParticipant.result = this.sessionTeams[teamNumber].result;
      }
    }
  }

  onTeamAssociationChange(sessionParticipantIndex: number, sessionParticipantId: number): void {
        let teamNumber = (this.sessionParticipants)[sessionParticipantIndex].teamNumber;
        this.sessionParticipants[sessionParticipantIndex].result = this.sessionTeams[teamNumber].result;
        this.sessionTeams[teamNumber].playerIds.push(sessionParticipantId);
      }

  onExpandChange() {
    this.expanded = !this.expanded;
  }

  //</editor-fold>


  //<editor-fold desc="Submit">
  @Output() sessionSubmit = new EventEmitter<GamePlaySessionDTO>();
  triggerSubmit(form: NgForm) {

    const gamePlayerSessionDTO = {
      sessionDateTime: this.sessionDateTime,
      scoringModel: this.scoringModel,
      gameId: this.gameId,
      sessionParticipantDTOSet: this.sessionParticipants,
      sessionTeamDTOSet: this.sessionTeams,
      creatorId: this.creatorId
    }

    this.sessionSubmit.emit(gamePlayerSessionDTO)
    form.resetForm();
  }
  //</editor-fold>

}


