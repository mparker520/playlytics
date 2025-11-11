import { Injectable } from '@angular/core';
import {GameResponseDTO} from '../dtos/game-response-dto';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  //<editor-fold desc = "Constructor">


  constructor(private http: HttpClient) {
  }

//</editor-fold>

  //<editor-fold desc = "Get Board Games from Database">
  getBoardGames(databaseFilter: string): Observable<any> {

    return this.http.get(`${environment.apiUrl}/board-games`, {params: {databaseFilter}, withCredentials: true})

  }

  //</editor-fold>

}
