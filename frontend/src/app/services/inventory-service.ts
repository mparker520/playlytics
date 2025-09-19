import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {OwnedGameResponseDTO} from '../dtos/owned-game-response-dto';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {



  constructor(private http: HttpClient) {

  }

  public getInventory(gameTitle: string): Observable<any> {

    return this.http.get<OwnedGameResponseDTO[]>('http://localhost:8080/owned-games', { params: {gameTitle: gameTitle}, withCredentials: true});

  }

}
