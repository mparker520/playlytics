import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GhostPlayerResponseDTO} from '../../../../dtos/ghost-player-response-dto';

@Component({
  selector: 'app-associations-list-component',
  imports: [],
  templateUrl: './associations-list-component.html',
  styleUrl: './associations-list-component.css'
})
export class AssociationsListComponent {
  @Input() associations!: GhostPlayerResponseDTO[];
  @Output() remove = new EventEmitter<number>;

  triggerRemove(id: number) {
    this.remove.emit(id)
  }

}
