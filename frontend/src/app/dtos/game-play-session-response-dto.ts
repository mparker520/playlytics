import {ScoringModelEnum} from '../enums/scoring-model-enum';

import {PlayerResponseDTO} from './PlayerResponseDTO';

export interface GamePlaySessionResponseDTO {
  id: number;
  sessionDateTime: string;
  scoringModel: ScoringModelEnum;
  gameName: string;
  sessionParticipantDetails: Array<PlayerResponseDTO>;
}
