import {ConnectionRequestStatusEnum} from '../enums/connection-request-status-enum';

export interface ConfirmedConnectionResponseDTO {
  peerAId: number;
  peerBId: number;
  connectionRequestStatus: ConnectionRequestStatusEnum;
}
