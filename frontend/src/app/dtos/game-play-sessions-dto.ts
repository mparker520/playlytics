import {ScoringModelEnum} from '../enums/scoring-model-enum';
import {SessionParticipantDTO} from './session-participant-dto';
import {SessionTeamDTO} from './session-team-dto';

export interface GamePlaySessionsDTO {
  sessionDateTime: string;
  scoringModel: ScoringModelEnum;
  creatorId: number;
  gameId: number;
  sessionParticipantDTOSet: Array<SessionParticipantDTO>;
  sessionTeamDTOSet: Array<SessionTeamDTO>
}
