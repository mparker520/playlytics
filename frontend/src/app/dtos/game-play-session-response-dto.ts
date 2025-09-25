import {ScoringModelEnum} from '../enums/scoring-model-enum';
import {SessionParticipantDTO} from './session-participant-dto';
import {SessionTeamDTO} from './session-team-dto';

export interface GamePlaySessionResponseDTO {
  id: number;
  sessionDateTime: string;
  scoringModel: ScoringModelEnum;
  gameName: string;
  sessionParticipantIds: Array<number>;
}
