import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GamePlaySessionDTO} from '../dtos/game-play-sessions-dto';
import {Observable} from 'rxjs';
import {GamePlaySessionResponseDTO} from '../dtos/game-play-session-response-dto';
import {environment} from '../../environments/environment';
import {GameResponseDTO} from '../dtos/game-response-dto';


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
    return this.http.post<GamePlaySessionDTO>(`${environment.apiUrl}/game-play-sessions`, gamePlaySessionDTO, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get All Played Games">
  public getAllPlayedGames(): Observable<GameResponseDTO[]>{

      return this.http.get<GameResponseDTO[]>(`${environment.apiUrl}/played-games`, {withCredentials: true});
}
  //</editor-fold>


  //<editor-fold desc="Get Game Play Sessions">
  public getGamePlaySessions() : Observable<GamePlaySessionResponseDTO[]> {
    return this.http.get<GamePlaySessionResponseDTO[]>(`${environment.apiUrl}/game-play-sessions`,  {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Get Game Play Sessions">
  public getPendingGamePlaySessions() : Observable<GamePlaySessionResponseDTO[]> {
    return this.http.get<GamePlaySessionResponseDTO[]>(`${environment.apiUrl}/pending-game-play-sessions`,  {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Delete Game Play Session">
  public deleteByIdAndPlayerId(sessionId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/game-play-sessions/${sessionId}`, {withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Accept Game Play Session">
  public acceptGamePlaySession(id: number): Observable<void> {
    return this.http.patch<void>(`${environment.apiUrl}/game-play-sessions/${id}`, {}, {withCredentials: true})
  }
  //</editor-fold>


}
