import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GhostPlayerResponseDTO} from '../dtos/ghost-player-response-dto';
import {Observable} from 'rxjs';
import {RegisteredPlayerResponseDTO} from '../dtos/registered-player-response-dto';
import {ConnectionRequestResponseDTO} from '../dtos/connection-request-response-dto';
import {ConfirmedConnectionResponseDTO} from '../dtos/confirmed-connection-response-dto';

@Injectable({
  providedIn: 'root'
})
export class NetworkService {

  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>

  //<editor-fold desc="Get All Associations">
  public getAllAssociations(): Observable<GhostPlayerResponseDTO[]> {
    return this.http.get<GhostPlayerResponseDTO[]>('/associations', {withCredentials: true});
}
  //</editor-fold>

  //<editor-fold desc="Remove Association">
  public removeAssociation(id: number): Observable<void> {
    return this.http.delete<void>(`/associations/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Guest / Ghost Players for Connection">
  public getGuestPlayers(identifierEmail: string): Observable<GhostPlayerResponseDTO> {

    return this.http.get<GhostPlayerResponseDTO>("/ghost-players", {params: {identifierEmail}, withCredentials: true})

  }
  //</editor-fold>

  //<editor-fold desc="Add Association">
  public addAssociation(id: number): Observable<GhostPlayerResponseDTO> {
    return this.http.post<GhostPlayerResponseDTO>(`/associations/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get All Connections">
  public getAllConnections(): Observable<RegisteredPlayerResponseDTO[]> {
        return this.http.get<RegisteredPlayerResponseDTO[]>('/connections', {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Get Sent Connection Requests">
  public getSentConnectionRequests(): Observable<ConnectionRequestResponseDTO[]> {
    return this.http.get<ConnectionRequestResponseDTO[]>('/sent-connection-requests', {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Pending Connection Requests">
  public getPendingConnectionRequests(): Observable<ConnectionRequestResponseDTO[]> {
    return this.http.get<ConnectionRequestResponseDTO[]>('/pending-connection-requests', {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Cancel Sent Connection Requests">
  public cancelConnectionRequest(id: number): Observable<void> {
    return this.http.delete<void>(`/cancel-connection-request/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Create Confirmed Connection">
  public createConfirmedConnection(id: number): Observable<ConfirmedConnectionResponseDTO> {
    return this.http.post<ConfirmedConnectionResponseDTO>(`/connections/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Decline Connection Request">
  public declineConnectionRequest(id: number): Observable<void> {
    return this.http.delete<void>(`/decline-connection-request/${id}`, {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Remove Connection">
  public removeConnection(peerId: number): Observable<void> {
        return this.http.delete<void>(`/connections/${peerId}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Block Player">
  public blockRegisteredPlayer(id: number): Observable<void> {
    return this.http.post<void>(`/block/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Lookup Registered Players">
  public discoverPeers(filter: string): Observable<RegisteredPlayerResponseDTO> {
    return this.http.get<RegisteredPlayerResponseDTO>("/players", {params: {filter}, withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Send Connection Request">
  public sendConnectionRequest(peerId: number): Observable<ConnectionRequestResponseDTO> {
    return this.http.post<ConnectionRequestResponseDTO>(`/connection-request/${peerId}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Blocks">
  public getBlocks(): Observable<RegisteredPlayerResponseDTO[]> {
    return this.http.get<RegisteredPlayerResponseDTO[]>('/blocks', {withCredentials: true})
  }
  //</editor-fold>

  public removeBlock(id: number): Observable<void> {
    return this.http.delete<void>(`/block/${id}`, {withCredentials: true})
  }

}
