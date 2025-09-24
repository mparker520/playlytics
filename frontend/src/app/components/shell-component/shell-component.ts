import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'app-shell-component',
  imports: [
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './shell-component.html',
  styleUrl: './shell-component.css'
})
export class ShellComponent {

  menuOpen = false;

  constructor(private authService: AuthService) {

  }

  get isAuthenticated(): boolean {
    return this.authService.authenticated;
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

}
