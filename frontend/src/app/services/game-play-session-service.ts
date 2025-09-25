import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GamePlaySessionDTO} from '../dtos/game-play-sessions-dto';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GamePlaySessionService {



  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>

  public createGamePlaySession(gamePlaySessionDTO: GamePlaySessionDTO): Observable<GamePlaySessionDTO> {
    return this.http.post<GamePlaySessionDTO>('/game-play-sessions', gamePlaySessionDTO, {withCredentials: true})
  }



}
