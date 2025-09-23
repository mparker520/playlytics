import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ScoringModelEnum} from '../../../enums/scoring-model-enum';
import {SessionParticipantDTO} from '../../../dtos/session-participant-dto';
import {SessionTeamDTO} from '../../../dtos/session-team-dto';
import {ConnectionDTO} from '../../../dtos/connection-dto';


@Component({
  selector: 'app-add-game-play-session-component',
  imports: [
    FormsModule
  ],
  templateUrl: './add-game-play-session-component.html',
  styleUrl: './add-game-play-session-component.css'
})
export class AddGamePlaySessionComponent {


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
  //</editor-fold>

  //<editor-fold desc="GamePlaySessionDTO Constructor">
  sessionDateTime?: string;
  scoringModel: ScoringModelEnum = ScoringModelEnum.RANKING;
  gameId?: number;
  sessionParticipants: SessionParticipantDTO[] = []
  sessionTeams: SessionTeamDTO[] = [];


  gamePlayerSessionDTO = {
      sessionDateTime: this.sessionDateTime,
      scoringModel: this.scoringModel,
      gameId: this.gameId,
      sessionParticipantDTOSet: this.sessionParticipants,
      sessionTeamDTOSet: this.sessionTeams

  }
  //</editor-fold>

  //<editor-fold desc="On Change Methods">
  onNumPlayerChange(): void {
      this.sessionParticipants = Array.from({length: this.numberPlayers}, (_, i) =>
        this.sessionParticipants[i] || {result: 0, playerId: 0, teamNumber: 0}
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

  onTeamAssociationChange(): void {
      for(const sessionParticipant of this.sessionParticipants) {
        let teamNumber = sessionParticipant.teamNumber;
        console.log(sessionParticipant.result = this.sessionTeams[teamNumber].result);
        sessionParticipant.result = this.sessionTeams[teamNumber].result;
      }
  }

  //</editor-fold>


}


