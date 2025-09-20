import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GhostPlayerResponseDTO} from '../dtos/ghost-player-response-dto';
import {Observable} from 'rxjs';

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


  public addAssociation(id: number): Observable<GhostPlayerResponseDTO> {
    return this.http.post<GhostPlayerResponseDTO>(`/associations/${id}`, {withCredentials: true})
  }

}
