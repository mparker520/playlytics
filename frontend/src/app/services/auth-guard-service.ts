import { Injectable } from '@angular/core';
import {

  CanActivate,
  Router,
  UrlTree
} from '@angular/router';
import {AuthService} from './auth-service';
import {catchError, map, Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements  CanActivate{


  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(): Observable<boolean | UrlTree> {

        return this.authService.loadCurrentUser().pipe(
          map(() => true),
          catchError(() => of(this.router.createUrlTree(['/login'])))
        )

  }

}
