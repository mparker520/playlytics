import {ScoringModelEnum} from '../enums/scoring-model-enum';
import {SessionParticipantDTO} from './session-participant-dto';

export interface SessionTeamDTO {
    result: number;
    playerIds: Array<number>;
    teamName: string;
}
