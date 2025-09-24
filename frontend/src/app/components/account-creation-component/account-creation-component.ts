import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AccountCreationService} from '../../services/account-creation-service';
import {Router} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-account-creation-component',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgOptimizedImage
  ],
  templateUrl: './account-creation-component.html',
  styleUrl: './account-creation-component.css'
})
export class AccountCreationComponent {

  loginEmail: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  displayName: string = '';
  avatar?: Uint8Array;


  constructor( private accountCreationService: AccountCreationService, private router: Router) {

  }

  createAccount() {

    const registeredPlayerDTO = {
      loginEmail: this.loginEmail,
      password: this.password,
      firstName: this.firstName,
      lastName: this.lastName,
      displayName: this.displayName,
      avatar: this.avatar
    }

    this.accountCreationService.createAccount(registeredPlayerDTO).subscribe({

        next: (response) => {
          console.log('Account Created');
          this.router.navigate(['/login']);
        },
        error: (error) => console.log('Failed to Authenticate')

      }
    );

  }


}
