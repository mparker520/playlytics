import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ScoringModelEnum} from '../../../enums/scoring-model-enum';
import {SessionParticipantDTO} from '../../../dtos/session-participant-dto';
import {SessionTeamDTO} from '../../../dtos/session-team-dto';
// @ts-ignore
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
  connections: ConnectionDTO[]=[
    {id: 1, firstName: 'Melissa', lastName: 'Parker', displayName: 'The Coding Wabs', email: 'melissaparker520@gmail.com'},
    {id: 2, firstName: 'Joe', lastName: 'Parker', displayName: 'The Dice Master', email: 'sample@gmail.com'}
  ];

  numberPlayers: number = 0;
  numberTeams: number = 0;
  cooperativeResult: number = 1;


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


}


export interface ConnectionDTO {
  id: number;
  firstName: string;
  lastName: string;
  avatar: Uint8Array
  email: string;
  displayName: string,
}
