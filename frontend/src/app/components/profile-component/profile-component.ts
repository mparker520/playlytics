import {Component, Input, OnInit} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../dtos/registered-player-response-dto';
import {RegisteredPlayerService} from '../../services/registered-player-service';
import {FormsModule, NgForm, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';
import {LogoutService} from '../../services/logout-service';


@Component({
  selector: 'app-profile-component',
  imports: [
    FormsModule,
    ReactiveFormsModule

  ],
  templateUrl: './profile-component.html',
  styleUrl: './profile-component.css'
})
export class ProfileComponent implements OnInit {

  confirmTriggered: boolean = false;
  @Input() profileInformation?: RegisteredPlayerResponseDTO;

  loginEmail: string = '';
  firstName: string = '';
  lastName: string = '';
  displayName: string = '';
  errorMessage?: string;


constructor(private registeredPlayerService: RegisteredPlayerService, private logoutService: LogoutService, private authService: AuthService, private router: Router) {

}

ngOnInit(): void {
  this.registeredPlayerService.getSelf().subscribe({
    next: (response: RegisteredPlayerResponseDTO) => {

      this.profileInformation = response;
    },
    error: (error: any) => console.error("fail", error)
  })
}


  triggerConfirm() {
    this.confirmTriggered = true;
  }

  triggerDelete() {
    this.registeredPlayerService.deleteRegisteredPlayer().subscribe({
      next:(response: void) => {
        console.log("deleting player")
            this.logoutService.logout().subscribe({
              next:(response: void) => {
                this.authService.authenticated = false;
                this.authService.currentUser = undefined;
                this.router.navigate(['/login']);
              },
              error: (error: any) => console.error("fail", error)
            })

      },
      error: (error: any) => console.error('fail', error)
    })
  }

  triggerKeepAccount() {
    this.confirmTriggered = false;
  }

  updateAccount(form: NgForm) {

    const registeredPlayerUpdateDTO = {
      loginEmail: this.loginEmail,
      firstName: this.firstName,
      lastName: this.lastName,
      displayName: this.displayName,
    }

    this.registeredPlayerService.updateRegisteredPlayer(registeredPlayerUpdateDTO).subscribe({
            next: (response: RegisteredPlayerResponseDTO) => {
                this.profileInformation = response;
          },
      error: (error: any) => {

        this.errorMessage = error.error.message

        setTimeout(() => {
          this.errorMessage = undefined;
        }, 3000);
      }
    })

    form.resetForm();

  }



}
