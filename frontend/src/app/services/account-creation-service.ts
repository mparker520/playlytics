import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RegisteredPlayerDTO} from '../dtos/registered-player-dto';

@Injectable({
  providedIn: 'root'
})
export class AccountCreationService {


  constructor(private http: HttpClient) {
  }

  public createAccount(registeredPlayerDTO: {
    loginEmail: string;
    password: string;
    firstName: string;
    lastName: string;
    displayName: string;
    avatar: Uint8Array<ArrayBufferLike> | undefined
  }): Observable<any>{

    return this.http.post('/create-account', registeredPlayerDTO);

  }


}
