import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GamePlaySessionDTO} from '../dtos/game-play-sessions-dto';
import {Observable} from 'rxjs';
import {GamePlaySessionResponseDTO} from '../dtos/game-play-session-response-dto';

@Injectable({
  providedIn: 'root'
})
export class GamePlaySessionService {



  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>

  //<editor-fold desc="Create Game Play Session">
  public createGamePlaySession(gamePlaySessionDTO: GamePlaySessionDTO): Observable<GamePlaySessionDTO> {
    return this.http.post<GamePlaySessionDTO>('/game-play-sessions', gamePlaySessionDTO, {withCredentials: true})
  }
  //</editor-fold>


  public getGamePlaySessions() : Observable<GamePlaySessionResponseDTO[]> {
    return this.http.get<GamePlaySessionResponseDTO[]>('/game-play-sessions',  {withCredentials: true});
  }



}
