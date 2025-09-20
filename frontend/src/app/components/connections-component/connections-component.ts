import { Component } from '@angular/core';
import {GuestPlayersComponent} from './guest-players-component/guest-players-component';
import {RegisteredPlayersComponent} from './registered-players-component/registered-players-component';

@Component({
  selector: 'app-connections-component',
  imports: [
    GuestPlayersComponent,
    RegisteredPlayersComponent
  ],
  templateUrl: './connections-component.html',
  styleUrl: './connections-component.css'
})
export class ConnectionsComponent {

}
