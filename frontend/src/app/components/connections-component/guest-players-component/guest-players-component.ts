import {Component, OnInit} from '@angular/core';
import {AssociationsListComponent} from './associations-list-component/associations-list-component';
import {AddAssociationComponent} from './add-association-component/add-association-component';
import {GhostPlayerResponseDTO} from '../../../dtos/ghost-player-response-dto';
import {NetworkService} from '../../../services/network-service';
import {CreateGuestComponent} from './create-guest-component/create-guest-component';
import {GhostPlayerDTO} from '../../../dtos/ghost-player-dto';
import {GhostPlayerService} from '../../../services/ghost-player-service';


@Component({
  selector: 'app-guest-players-component',
  imports: [
    AssociationsListComponent,
    AddAssociationComponent,
    CreateGuestComponent
  ],
  templateUrl: './guest-players-component.html',
  styleUrl: './guest-players-component.css'
})
export class GuestPlayersComponent implements OnInit {

  //<editor-fold desc="Constructor and Fields">
  associations: GhostPlayerResponseDTO[] = [];
  guestPlayer?: GhostPlayerResponseDTO;
  databaseFilter: string = '';

  constructor(private networkService: NetworkService, private ghostPlayerService: GhostPlayerService) {

  }

  //</editor-fold>

  //<editor-fold desc="On Initiate, Set Up Data">
  ngOnInit() {

    this.networkService.getAllAssociations().subscribe({
      next: (response: GhostPlayerResponseDTO[]) => {
        this.associations = response;
      },
      error: (error: any) => console.error("fail", error)
    })

  }

  //</editor-fold>

  //<editor-fold desc="Remove Associations">
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
  //</editor-fold>

  //<editor-fold desc="Get Guest / Ghost Player from Database">
  handleLookup(databaseFilter: string) {
    this.networkService.getGuestPlayers(databaseFilter).subscribe({
      next:(response: GhostPlayerResponseDTO) => {
        this.guestPlayer = response;
      },
      error: (error: any) => console.error("fail", error)
    })
  }
  //</editor-fold>

  //<editor-fold desc="Add Ghost/Guest Player as Associate">
  handleAdd(id: number) {
        this.networkService.addAssociation(id).subscribe({
          next: (response: GhostPlayerResponseDTO) => {
            this.networkService.getAllAssociations().subscribe({
              next: (updateResponse: GhostPlayerResponseDTO[]) => {
                this.associations = updateResponse;
                this.guestPlayer = undefined;
              },
              error: (error: any) => console.error("fail", error)
        })
      }
    })
  }
  //</editor-fold>


  //<editor-fold desc="Create Guest / Ghost Player">
  handleCreate(ghostPlayerDTO: GhostPlayerDTO) {
      this.ghostPlayerService.createNewGhostPlayer(ghostPlayerDTO).subscribe({
        next: (createResponse: GhostPlayerResponseDTO) => {
          this.networkService.getAllAssociations().subscribe({
            next:(updateResponse: GhostPlayerResponseDTO[]) => {
              this.associations = updateResponse;
            }
          })
        }
      })
  }
  //</editor-fold>

}
