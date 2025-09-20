import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GhostPlayerResponseDTO} from '../dtos/ghost-player-response-dto';
import {GhostPlayerDTO} from '../dtos/ghost-player-dto';

@Injectable({
  providedIn: 'root'
})
export class GhostPlayerService {

  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>



  public createNewGhostPlayer(ghostPlayerDTO: GhostPlayerDTO): Observable<GhostPlayerResponseDTO> {
    return this.http.post<GhostPlayerResponseDTO>('/ghost-players', ghostPlayerDTO);
  }


}
