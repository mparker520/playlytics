import { Injectable } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  authenticated = false;
  currentUser?: any;

  loadCurrentUser(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/me`, {withCredentials: true})
      .pipe(
        tap(user => {
          this.currentUser = user;
          this.authenticated = true;
        })
      );
  }

}
