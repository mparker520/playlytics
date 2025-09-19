import {Component, OnInit} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {LoginService} from '../../services/login-service';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-component',
  imports: [
    FormsModule
  ],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';


  constructor( private loginService: LoginService, private authService: AuthService, private router: Router) {

  }

  login() {

    const authRequestDTO = {
      username: this.username,
      password: this.password
    }

    this.loginService.login(authRequestDTO).subscribe({

      next: (response) => {
        console.log('Login Success');
        this.authService.authenticated = true;
        this.router.navigate(['/home']);
      },
      error: (error) => console.log('Failed to Authenticate')

      }
    );

  }



}
