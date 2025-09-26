import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PlayerResponseDTO} from '../dtos/PlayerResponseDTO';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisteredPlayerService {

  constructor(private http: HttpClient) {
  }

  public getSelf(): Observable<PlayerResponseDTO> {
    return this.http.get<PlayerResponseDTO>('/profile', {withCredentials: true})
  }


}
