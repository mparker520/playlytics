import {ConnectionRequestStatusEnum} from '../enums/connection-request-status-enum';

export interface ConnectionRequestResponseDTO {
  id: number;
  senderId: number;
  senderFirstName: string;
  senderLastName: string;
  senderEmail: string;
  recipientId: number;
  recipientFirstName: string;
  recipientLastName: string;
  recipientEmail: string;
  connectionRequestStatus: ConnectionRequestStatusEnum;
}

