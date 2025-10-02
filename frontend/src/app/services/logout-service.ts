import { Injectable } from '@angular/core';
import {AuthRequestDTO} from '../dtos/auth-request-dto';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {


  constructor(private http: HttpClient) {
  }

  public logout() : Observable<any> {

    return this.http.post(`${environment.apiUrl}/logout`, {}, {withCredentials: true});
  }

}



