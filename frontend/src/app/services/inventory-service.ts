import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {OwnedGameResponseDTO} from '../dtos/owned-game-response-dto';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {


  //<editor-fold desc = "Constructor">

  constructor(private http: HttpClient) {

  }

  //</editor-fold>

  //<editor-fold desc = "Get All Owned Games"
  public getInventory(): Observable<OwnedGameResponseDTO[]> {

    return this.http.get<OwnedGameResponseDTO[]>(`${environment.apiUrl}/owned-games`, {withCredentials: true});

  }

  //</editor-fold>

  //<editor-fold desc="Add Game to Inventory">
  public addOwnedGame(gameId: number): Observable<OwnedGameResponseDTO> {
    return this.http.post<OwnedGameResponseDTO>(`/owned-games/${gameId}`, {}, {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc = "Delete OwnedGame">
  public deleteOwnedGame(id: number): Observable<void> {

    return this.http.delete<void>(`/owned-games/${id}`, {withCredentials: true});

  }

  //</editor-fold>

}
