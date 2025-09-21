import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GhostPlayerResponseDTO} from '../dtos/ghost-player-response-dto';
import {Observable} from 'rxjs';
import {RegisteredPlayerResponseDTO} from '../dtos/registered-player-response-dto';
import {ConnectionRequestResponseDTO} from '../dtos/connection-request-response-dto';

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

  //<editor-fold desc="Cancel Sent Connection Requests">
  public cancelConnectionRequest(id: number): Observable<void> {
    return this.http.delete<void>(`/connection-request/${id}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Remove Connection">
  public removeConnection(id: number): Observable<void> {
        return this.http.delete<void>(`/connections/${id}`, {withCredentials: true})
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

}
