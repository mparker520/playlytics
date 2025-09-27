import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RegisteredPlayerResponseDTO} from '../dtos/registered-player-response-dto';


@Injectable({
  providedIn: 'root'
})
export class RegisteredPlayerService {

  constructor(private http: HttpClient) {
  }

  public getSelf(): Observable<RegisteredPlayerResponseDTO> {
    return this.http.get<RegisteredPlayerResponseDTO>('/profile', {withCredentials: true})
  }


}
