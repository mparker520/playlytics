import {Component, OnInit} from '@angular/core';
import {AddAssociationComponent} from "../guest-players-component/add-association-component/add-association-component";
import {
    AssociationsListComponent
} from "../guest-players-component/associations-list-component/associations-list-component";
import {CreateGuestComponent} from "../guest-players-component/create-guest-component/create-guest-component";
import {ConfirmedConnectionsComponent} from './confirmed-connections-component/confirmed-connections-component';
import {ManageConnectionsComponent} from './manage-connections-component/manage-connections-component';
import {RegisteredPlayerResponseDTO} from '../../../dtos/registered-player-response-dto';
import {NetworkService} from '../../../services/network-service';
import {GhostPlayerResponseDTO} from '../../../dtos/ghost-player-response-dto';
import {ConnectionRequestResponseDTO} from '../../../dtos/connection-request-response-dto';

@Component({
  selector: 'app-registered-players-component',
  imports: [
    ConfirmedConnectionsComponent,
    ManageConnectionsComponent
  ],
  templateUrl: './registered-players-component.html',
  styleUrl: './registered-players-component.css'
})
export class RegisteredPlayersComponent implements OnInit {

  //<editor-fold desc="Constructor and Fields">

  connections?: RegisteredPlayerResponseDTO[];
  registeredPlayer?: RegisteredPlayerResponseDTO;
  sentRequests?: ConnectionRequestResponseDTO[];
  pendingRequests?: ConnectionRequestResponseDTO[];

  constructor(private networkService: NetworkService) {
  }

  //</editor-fold>

  //<editor-fold desc="On Initiate">
  ngOnInit() {

    this.networkService.getAllConnections().subscribe({
      next: (connectionsResponse: RegisteredPlayerResponseDTO[]) => {
        this.connections = connectionsResponse;
      },
      error: (error: any) => console.log("fail", error)
    })

    this.networkService.getSentConnectionRequests().subscribe({
      next: (sentRequestsResponse: ConnectionRequestResponseDTO[]) => {
        this.sentRequests = sentRequestsResponse;
      },
      error: (error: any) => console.log("fail", error)
    })

  }

  //</editor-fold>

  //<editor-fold desc="Remove Connection">
  handleRemove(id: number): void {
    this.networkService.removeConnection(id).subscribe({
      next: (response: void) => {
        this.networkService.getAllConnections().subscribe({
          next: (updateResponse: RegisteredPlayerResponseDTO[]) => {
            console.log(updateResponse);
            this.connections = updateResponse;
          },
          error: (error: any) => console.error("fail", error)
        })
      }
    })
  }

  //</editor-fold>

  //<editor-fold desc="Block Registered Player">
  handleBlock(id: number): void {
    console.log("removeBlock");
    this.networkService.blockRegisteredPlayer(id).subscribe({
      next: (response: void) => {
        this.networkService.getAllConnections().subscribe({
          next: (updateResponse: RegisteredPlayerResponseDTO[]) => {
            this.connections = updateResponse;
          },
          error: (error: any) => console.error("fail", error)
        })
      },
      error: (error: any) => console.error("fail", error)
    })

  }

  //</editor-fold>

  //<editor-fold desc="Get RegisteredPlayer from Database">
  handleLookup(databaseFilter: string) {
    this.networkService.discoverPeers(databaseFilter).subscribe({
      next: (response: RegisteredPlayerResponseDTO) => {
        this.registeredPlayer = response;
      },
      error: (error: any) => console.error("fail", error)
    })
  }

  //</editor-fold>

  //<editor-fold desc="Send Connection Request">
  handleSend(id: number) {
    this.networkService.sendConnectionRequest(id).subscribe({
      next: (registeredPlayerResponse: ConnectionRequestResponseDTO) => {
        this.registeredPlayer = undefined;
        this.networkService.getSentConnectionRequests().subscribe({
          next: (sentRequestsResponse: ConnectionRequestResponseDTO[]) => {
            this.sentRequests = sentRequestsResponse;
          },
          error: (error: any) => console.error("fail", error)
        })
      }
    })
  }

  //</editor-fold>

  handleCancel(id: number) {
    this.networkService.cancelConnectionRequest(id).subscribe({
      next: (cancelResponse: void) => {
        this.networkService.getSentConnectionRequests().subscribe({
          next: (sentRequestsResponse: ConnectionRequestResponseDTO[]) => {
            this.sentRequests = sentRequestsResponse;
          },
          error: (error: any) => console.error("fail", error)
        })
      }
    })
  }
}
