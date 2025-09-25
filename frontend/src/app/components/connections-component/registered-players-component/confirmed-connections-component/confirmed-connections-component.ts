import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RegisteredPlayerResponseDTO} from '../../../../dtos/registered-player-response-dto';
import {NgOptimizedImage} from '@angular/common';




@Component({
  selector: 'app-confirmed-connections-component',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './confirmed-connections-component.html',
  styleUrl: './confirmed-connections-component.css'
})
export class ConfirmedConnectionsComponent {

  expandedConnectionsList: boolean = false;

    @Input() connections?: RegisteredPlayerResponseDTO[]

    @Output() remove = new EventEmitter<number>
    triggerRemove(id: number) {
      this.remove.emit(id);
    }

    @Output() block = new EventEmitter<number>
    triggerBlock(id: number) {
      this.block.emit(id);
    }

  onExpandConnectionsListChange() {
      this.expandedConnectionsList = !this.expandedConnectionsList;
  }

}
