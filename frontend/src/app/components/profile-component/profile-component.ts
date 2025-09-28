import {Component, Input, OnInit} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../dtos/registered-player-response-dto';
import {RegisteredPlayerService} from '../../services/registered-player-service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


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


constructor(private registeredPlayerService: RegisteredPlayerService) {

}

ngOnInit(): void {
  this.registeredPlayerService.getSelf().subscribe({
    next: (response: RegisteredPlayerResponseDTO) => {
      console.log(response)
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

      },
      error: (error: any) => console.error('fail', error)
    })
  }

  triggerKeepAccount() {
    this.confirmTriggered = false;
  }

  updateAccount() {

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
          error: (error: any) => console.error('fail', error)
    })


  }



}
