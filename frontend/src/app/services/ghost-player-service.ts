import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GhostPlayerResponseDTO} from '../dtos/ghost-player-response-dto';
import {GhostPlayerDTO} from '../dtos/ghost-player-dto';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GhostPlayerService {

  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>



  //<editor-fold desc="Create Ghost Player">
  public createNewGhostPlayer(ghostPlayerDTO: GhostPlayerDTO): Observable<GhostPlayerResponseDTO> {
    return this.http.post<GhostPlayerResponseDTO>(`${environment.apiUrl}/ghost-players`, ghostPlayerDTO, {withCredentials: true});
  }
  //</editor-fold>


}
