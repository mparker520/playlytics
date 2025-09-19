import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {OwnedGameResponseDTO} from '../dtos/owned-game-response-dto';

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

    return this.http.get<OwnedGameResponseDTO[]>('/owned-games', {withCredentials: true});

  }

  //</editor-fold>


  //<editor-fold desc = "Delete OwnedGame">
  public deleteOwnedGame(id: number): Observable<OwnedGameResponseDTO[]> {

    return this.http.delete<OwnedGameResponseDTO[]>(`/owned-games/${id}`, {withCredentials: true});

  }

  //</editor-fold>

}
