import {Component, Input, OnInit} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../dtos/registered-player-response-dto';
import {RegisteredPlayerService} from '../../services/registered-player-service';


@Component({
  selector: 'app-profile-component',
    imports: [

    ],
  templateUrl: './profile-component.html',
  styleUrl: './profile-component.css'
})
export class ProfileComponent implements OnInit {

  confirmTriggered: boolean = false;
  @Input() profileInformation?: RegisteredPlayerResponseDTO;

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
    console.log("fake delete");
  }

  triggerKeepAccount() {
    this.confirmTriggered = false;
  }




}
