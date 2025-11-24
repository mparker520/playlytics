import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BoardGamesService {

  //<editor-fold desc="Constructor">

    constructor(private http: HttpClient) {

    }


  //</editor-fold>

  //<editor-fold desc="Add Board Game Function">

    public addBoardGame(boardGame: string): Observable<string> {
        return this.http.post<string>(`${environment.apiUrl}/board-game`, boardGame, {withCredentials: true})
    }


  //</editor-fold>


}
