import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GhostPlayerResponseDTO} from '../../../../dtos/ghost-player-response-dto';
import {NgOptimizedImage} from "@angular/common";

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

  @Output() remove = new EventEmitter<number>;

  triggerRemove(id: number) {
    this.remove.emit(id)
  }

  onExpandAssociationsListChange() {
    this.expandedAssociationsList = !this.expandedAssociationsList;
  }

}
