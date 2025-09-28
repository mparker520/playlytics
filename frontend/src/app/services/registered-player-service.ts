import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RegisteredPlayerResponseDTO} from '../dtos/registered-player-response-dto';
import {RegisteredPlayerUpdateDTO} from '../dtos/RegisteredPlayerUpdateDTO';


@Injectable({
  providedIn: 'root'
})
export class RegisteredPlayerService {

  constructor(private http: HttpClient) {
  }

  //<editor-fold desc="Get Profile Information">
  public getSelf(): Observable<RegisteredPlayerResponseDTO> {
    return this.http.get<RegisteredPlayerResponseDTO>('/profile', {withCredentials: true})
  }

  //</editor-fold>

  //<editor-fold desc="Update Registered Player">
  public updateRegisteredPlayer(updateDetails: {
    loginEmail: string;
    firstName: string;
    lastName: string;
    displayName: string;

  }): Observable<RegisteredPlayerResponseDTO> {
    return this.http.patch<RegisteredPlayerResponseDTO>('/profile', updateDetails, {withCredentials: true});
  }
  //</editor-fold>

  //<editor-fold desc="Delete Registered Player">
  public deleteRegisteredPlayer(): Observable<void> {
    return this.http.delete<void>('/profile', {withCredentials: true})
  }
  //</editor-fold>



}
