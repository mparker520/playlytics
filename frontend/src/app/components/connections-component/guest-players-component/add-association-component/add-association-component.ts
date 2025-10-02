import {Component, EventEmitter, Input, Output} from '@angular/core';
import {GhostPlayerResponseDTO} from '../../../../dtos/ghost-player-response-dto';

@Component({
  selector: 'app-add-association-component',
  imports: [],
  templateUrl: './add-association-component.html',
  styleUrl: './add-association-component.css'
})
export class AddAssociationComponent {
  @Input() guestPlayer?: GhostPlayerResponseDTO;
  @Input() addGhostErrorMessage?: string;
  @Input() lookUpGhostErrorMessage?: string;

  @Output() lookup = new EventEmitter<string>;
  triggerLookup(searchValue: string) {
    this.lookup.emit(searchValue);
  }

  @Output() add = new EventEmitter<number>;
  triggerAdd(id: number) {
    this.add.emit(id)
  }

}
