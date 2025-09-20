import {Component, OnInit} from '@angular/core';
import {AssociationsListComponent} from './associations-list-component/associations-list-component';
import {AddAssociationComponent} from './add-association-component/add-association-component';
import {AddGuestComponent} from './add-guest-component/add-guest-component';
import {GhostPlayerResponseDTO} from '../../../dtos/ghost-player-response-dto';
import {NetworkService} from '../../../services/network-service';
import {OwnedGameResponseDTO} from '../../../dtos/owned-game-response-dto';

@Component({
  selector: 'app-guest-players-component',
  imports: [
    AssociationsListComponent,
    AddAssociationComponent,
    AddGuestComponent
  ],
  templateUrl: './guest-players-component.html',
  styleUrl: './guest-players-component.css'
})
export class GuestPlayersComponent implements OnInit {

  //<editor-fold desc="Constructor and Fields">
  associations: GhostPlayerResponseDTO[] = [];

  constructor(private networkService: NetworkService) {

  }

  //</editor-fold>

  //<editor-fold desc="Get All Associations">
  ngOnInit() {

    this.networkService.getAllAssociations().subscribe({
      next: (response: GhostPlayerResponseDTO[]) => {
        console.log(response);
        this.associations = response;
        console.log(this.associations);
      },
      error: (error: any) => console.error("fail", error)
    })

  }

  //</editor-fold>


  handleRemove(id: number) {
    this.networkService.removeAssociation(id).subscribe({
      next: (removeResponse: void) => {
        this.networkService.getAllAssociations().subscribe({
          next: (updateResponse: GhostPlayerResponseDTO[]) => {
            this.associations = updateResponse;
          },
          error: (error: any) => console.error("fail", error)
        })
      }
    })
  }

}
