import {ScoringModelEnum} from '../enums/scoring-model-enum';
import {SessionParticipantDTO} from './session-participant-dto';


export interface GamePlaySessionDTO {
  sessionDateTime: string;
  scoringModel: ScoringModelEnum;
  gameId: number;
  sessionParticipantDTOSet: Array<SessionParticipantDTO>;

}
//  sessionTeamDTOSet: Array<SessionTeamDTO>
