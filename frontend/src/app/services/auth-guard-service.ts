import { Injectable } from '@angular/core';
import {

  CanActivate,
  Router,
  UrlTree
} from '@angular/router';
import {AuthService} from './auth-service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements  CanActivate{


  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(): boolean | UrlTree {

    if (this.authService.authenticated) {
      return true;
    }

    return this.router.createUrlTree(['/login']);

  }

}
