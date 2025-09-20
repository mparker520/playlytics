import { Component } from '@angular/core';
import {GuestPlayersComponent} from './guest-players-component/guest-players-component';

@Component({
  selector: 'app-connections-component',
  imports: [
    GuestPlayersComponent
  ],
  templateUrl: './connections-component.html',
  styleUrl: './connections-component.css'
})
export class ConnectionsComponent {

}
