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

  public removeAssociation(id: number): Observable<void> {
    return this.http.delete<void>(`/remove-association/${id}`, {withCredentials: true})
  }


}
