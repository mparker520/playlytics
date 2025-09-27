import {Component, OnInit} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {LoginService} from '../../services/login-service';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-login-component',
  imports: [
    FormsModule,
    NgOptimizedImage
  ],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  errorMessage?: string;

  constructor( private loginService: LoginService, private authService: AuthService, private router: Router) {

  }

  login() {

    const authRequestDTO = {
      username: this.username,
      password: this.password
    }

    this.loginService.login(authRequestDTO).subscribe({

      next: (response) => {
        this.authService.authenticated = true;
        this.router.navigate(['/home']);
      },

      error: (error: any) =>  {
              this.errorMessage = "You do not have an Account or Your Credentials are Not Valid."
        }


      }
    );

  }



}
