import {ScoringModelEnum} from '../enums/scoring-model-enum';
import {SessionParticipantDTO} from './session-participant-dto';
import {SessionTeamDTO} from './session-team-dto';
import {PlayerResponseDTO} from './PlayerResponseDTO';

export interface GamePlaySessionResponseDTO {
  id: number;
  sessionDateTime: string;
  scoringModel: ScoringModelEnum;
  gameName: string;
  sessionParticipantDetails: Array<PlayerResponseDTO>;
}
