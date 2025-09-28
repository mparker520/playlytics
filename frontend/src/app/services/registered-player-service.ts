import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RegisteredPlayerResponseDTO} from '../dtos/registered-player-response-dto';
import {RegisteredPlayerUpdateDTO} from '../dtos/RegisteredPlayerUpdateDTO';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class RegisteredPlayerService {

  constructor(private http: HttpClient) {
  }

  //<editor-fold desc="Get Profile Information">
  public getSelf(): Observable<RegisteredPlayerResponseDTO> {
    return this.http.get<RegisteredPlayerResponseDTO>(`${environment.apiUrl}/profile`, {withCredentials: true})
  }

  //</editor-fold>

  //<editor-fold desc="Update Registered Player">
  public updateRegisteredPlayer(updateDetails: {
    loginEmail: string;
    firstName: string;
    lastName: string;
    displayName: string;

  }): Observable<RegisteredPlayerResponseDTO> {
    return this.http.patch<RegisteredPlayerResponseDTO>(`${environment.apiUrl}/profile`, updateDetails, {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Delete Registered Player">
  public deleteRegisteredPlayer(): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/profile`, {withCredentials: true})
  }
  //</editor-fold>



}
