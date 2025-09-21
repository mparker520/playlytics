import {ConnectionRequestStatusEnum} from '../enums/connection-request-status-enum';

export interface ConnectionRequestResponseDTO {
  senderId: number;
  receiverId: number;
  connectionRequestStatus: ConnectionRequestStatusEnum;
}
