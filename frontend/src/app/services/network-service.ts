import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GhostPlayerResponseDTO} from '../dtos/ghost-player-response-dto';
import {Observable} from 'rxjs';
import {RegisteredPlayerResponseDTO} from '../dtos/registered-player-response-dto';
import {ConnectionRequestResponseDTO} from '../dtos/connection-request-response-dto';
import {ConfirmedConnectionResponseDTO} from '../dtos/confirmed-connection-response-dto';
import {PlayerResponseDTO} from '../dtos/PlayerResponseDTO';
import {environment} from '../../environments/environment';

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
    return this.http.get<GhostPlayerResponseDTO[]>(`${environment.apiUrl}/associations`, {withCredentials: true});
}
  //</editor-fold>

  //<editor-fold desc="Remove Association">
  public removeAssociation(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/associations/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Guest / Ghost Players for Connection">
  public getGuestPlayers(identifierEmail: string): Observable<GhostPlayerResponseDTO> {

    return this.http.get<GhostPlayerResponseDTO>(`${environment.apiUrl}/ghost-players`, {params: {identifierEmail}, withCredentials: true})

  }
  //</editor-fold>

  //<editor-fold desc="Add Association">
  public addAssociation(id: number): Observable<GhostPlayerResponseDTO> {
    return this.http.post<GhostPlayerResponseDTO>(`${environment.apiUrl}/associations/${id}`, {}, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get All Connections">
  public getAllConnections(): Observable<RegisteredPlayerResponseDTO[]> {
        return this.http.get<RegisteredPlayerResponseDTO[]>(`${environment.apiUrl}/connections`, {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Get Sent Connection Requests">
  public getSentConnectionRequests(): Observable<ConnectionRequestResponseDTO[]> {
    return this.http.get<ConnectionRequestResponseDTO[]>(`${environment.apiUrl}/sent-connection-requests`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Pending Connection Requests">
  public getPendingConnectionRequests(): Observable<ConnectionRequestResponseDTO[]> {
    return this.http.get<ConnectionRequestResponseDTO[]>(`${environment.apiUrl}/pending-connection-requests`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Cancel Sent Connection Requests">
  public cancelConnectionRequest(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/cancel-connection-request/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Create Confirmed Connection">
  public createConfirmedConnection(id: number): Observable<ConfirmedConnectionResponseDTO> {
    return this.http.post<ConfirmedConnectionResponseDTO>(`${environment.apiUrl}/connections/${id}`, {}, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Decline Connection Request">
  public declineConnectionRequest(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/decline-connection-request/${id}`, {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Remove Connection">
  public removeConnection(peerId: number): Observable<void> {
        return this.http.delete<void>(`${environment.apiUrl}/connections/${peerId}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Block Player">
  public blockRegisteredPlayer(id: number): Observable<void> {
    return this.http.post<void>(`${environment.apiUrl}/block/${id}`, {}, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Lookup Registered Players">
  public discoverPeers(filter: string): Observable<RegisteredPlayerResponseDTO> {
    return this.http.get<RegisteredPlayerResponseDTO>(`${environment.apiUrl}/players`, {params: {filter}, withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Send Connection Request">
  public sendConnectionRequest(peerId: number): Observable<ConnectionRequestResponseDTO> {
    return this.http.post<ConnectionRequestResponseDTO>(`${environment.apiUrl}/connection-request/${peerId}`, {}, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Blocks">
  public getBlocks(): Observable<RegisteredPlayerResponseDTO[]> {
    return this.http.get<RegisteredPlayerResponseDTO[]>(`${environment.apiUrl}/blocks`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Remove Block">
  public removeBlock(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/block/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Entire Network">
  public getNetwork(): Observable<PlayerResponseDTO[]> {
    return this.http.get<PlayerResponseDTO[]>(`${environment.apiUrl}/network`, {withCredentials: true});
  }
  //</editor-fold>

}
