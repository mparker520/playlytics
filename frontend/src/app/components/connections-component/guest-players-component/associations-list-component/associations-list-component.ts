import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {GhostPlayerResponseDTO} from '../../../../dtos/ghost-player-response-dto';
import {NgOptimizedImage} from "@angular/common";
import {OwnedGameResponseDTO} from '../../../../dtos/owned-game-response-dto';

@Component({
  selector: 'app-associations-list-component',
    imports: [
        NgOptimizedImage
    ],
  templateUrl: './associations-list-component.html',
  styleUrl: './associations-list-component.css'
})
export class AssociationsListComponent {

  expandedAssociationsList: boolean = false;

  @Input() associations!: GhostPlayerResponseDTO[];

  filteredAssociations?: GhostPlayerResponseDTO[];

  ngOnChanges(changes: SimpleChanges) {
    if(changes['associations'] && this.associations) {
      this.filteredAssociations = this.associations;
    }
  }


  @Output() remove = new EventEmitter<number>;

  triggerRemove(id: number) {
    this.remove.emit(id)
  }

  onExpandAssociationsListChange() {
    this.expandedAssociationsList = !this.expandedAssociationsList;
  }

  triggerAssociationLookup(searchInventoryBox: string) {
    this.filteredAssociations = this.associations.filter(association => {
        const fullName = (association.firstName + " " + association.lastName).toLowerCase();
      console.log('Checking:', association.identifierEmail, fullName, searchInventoryBox);
        return association.identifierEmail.toLowerCase().includes(searchInventoryBox.toLowerCase()) ||
        fullName.toLowerCase().includes(searchInventoryBox.toLowerCase());
      }
    );
  }

  triggerFilterClear() {
    this.filteredAssociations = this.associations;
  }

}
