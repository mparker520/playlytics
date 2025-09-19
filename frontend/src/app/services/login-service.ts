import { Injectable } from '@angular/core';
import {AuthRequestDTO} from '../dtos/auth-request-dto';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  constructor(private http: HttpClient) {
  }

  public login(authRequestDTO: AuthRequestDTO) : Observable<any> {
    return this.http.post('/login', authRequestDTO, {withCredentials: true});
  }

}


//    return this.http.post('http://localhost:8080/login', authRequestDTO);
