import { Component } from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {AuthService} from '../../services/auth-service';
import {LogoutService} from '../../services/logout-service';


@Component({
  selector: 'app-shell-component',
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './shell-component.html',
  styleUrl: './shell-component.css'
})
export class ShellComponent {

  menuOpen = false;

  constructor(private authService: AuthService, private logoutService: LogoutService, private router: Router) {

  }

  get isAuthenticated(): boolean {
    return this.authService.authenticated;
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  logOut() {
    this.logoutService.logout().subscribe({
      next: () => {
        this.authService.authenticated = false;
        this.authService.currentUser = undefined;
        this.router.navigate(['/login']).then(r =>{} );
      },
      error: (error: any) => console.error('fail', error)
    })
  }

}
